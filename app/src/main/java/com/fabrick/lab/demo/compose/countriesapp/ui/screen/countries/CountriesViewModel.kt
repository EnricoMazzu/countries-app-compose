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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    data class UiFilterState(
        val open: Boolean = false,
        val contentLoading: Boolean = false,
        val filterValue: CountryFilters? = null
    )

    data class UiState (
        val pullToRefreshLoading: Boolean = false,
        val loading: Boolean = true,
        val countries: Countries = Collections.emptyList(),
        val error: Exception? = null,
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiFilterState: MutableStateFlow<UiFilterState> by lazy {
        val initialFilterValue = savedStateHandle[COUNTRY_FILTERS_KEY] ?: CountryFilters()
        MutableStateFlow(UiFilterState(
            filterValue = initialFilterValue
        ))
    }
    val uiFilterState: StateFlow<UiFilterState> by lazy { _uiFilterState.asStateFlow() }

    init {
        Timber.i("View Model Countries init ${this.hashCode()}")
        viewModelScope.launch {
            /*filters.collect {
                savedStateHandle[COUNTRY_FILTERS_KEY] = it
                countriesRepo.load(it)
            }*/

            uiFilterState.distinctUntilChangedBy {
                uiFilterState.value.filterValue
            }.collect {
                savedStateHandle[COUNTRY_FILTERS_KEY] = it.filterValue
                countriesRepo.load(it.filterValue)
            }
        }

        countriesRepo.countries.collectResourceStates(
            viewModelScope,
            onLoading = {
                _uiState.value = _uiState.value.copy(
                    loading = true
                )
            },
            onError = {
                Timber.e(it.getExceptionIfNotHandled())
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    pullToRefreshLoading = false,
                    error = it.getExceptionIfNotHandled()
                )
            },
            onSuccess = {
                _uiState.value = _uiState.value.copy(
                    loading = false,
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
            _uiState.value = uiState.value.copy(pullToRefreshLoading = true)
            val filter: CountryFilters? = _uiFilterState.value.filterValue
            countriesRepo.load(filter, true)
        }
    }

    companion object{
        const val COUNTRY_FILTERS_KEY = "COUNTRY_FILTERS_KEY"
    }
}