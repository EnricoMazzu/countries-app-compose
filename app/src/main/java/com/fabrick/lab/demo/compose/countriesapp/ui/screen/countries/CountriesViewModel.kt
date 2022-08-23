package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.common.collectResourceStates
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Continent
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Countries
import com.fabrick.lab.demo.compose.countriesapp.domain.model.CountryFilters
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Language
import com.fabrick.lab.demo.compose.countriesapp.domain.repo.CountriesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    data class UiState (
        val pullToRefreshLoading: Boolean = false,
        val onLoading: Boolean = true,
        val countries: Countries = Collections.emptyList(),
        val error: Exception? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val filters: MutableStateFlow<CountryFilters> by lazy {
        val initialValue = savedStateHandle[COUNTRY_FILTERS_KEY] ?: CountryFilters()
        MutableStateFlow(initialValue)
    }

    init {
        Timber.i("View Model Countries init ${this.hashCode()}")
        viewModelScope.launch {
            filters.collect {
                savedStateHandle[COUNTRY_FILTERS_KEY] = it
                countriesRepo.load(it)
            }
        }

        countriesRepo.countries.collectResourceStates(
            viewModelScope,
            onLoading = {
                _uiState.value = _uiState.value.copy(
                    onLoading = true
                )
            },
            onError = {
                _uiState.value = _uiState.value.copy(
                    onLoading = false,
                    pullToRefreshLoading = false,
                    error = it.getExceptionIfNotHandled()
                )
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    onLoading = false,
                    pullToRefreshLoading = false,
                    countries = it.data ?: Collections.emptyList()
                )
            }
        )
    }

    fun getCountries(): Flow<Resource<Countries>> = countriesRepo.countries

    fun getContinents(): Flow<Resource<List<Continent>>> = countriesRepo.getContinents()

    fun getLanguages():  Flow<Resource<List<Language>>> = countriesRepo.getLanguages()

    fun reload() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                pullToRefreshLoading = true
            )
            countriesRepo.load(filters.value, true)
        }
    }

    companion object{
        const val COUNTRY_FILTERS_KEY = "COUNTRY_FILTERS_KEY"
    }
}