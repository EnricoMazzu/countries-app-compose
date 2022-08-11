package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.data.CountriesRepo
import com.fabrick.lab.demo.compose.countriesapp.model.Continent
import com.fabrick.lab.demo.compose.countriesapp.model.Countries
import com.fabrick.lab.demo.compose.countriesapp.model.CountryFilters
import com.fabrick.lab.demo.compose.countriesapp.model.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepo: CountriesRepo,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val filters: MutableStateFlow<CountryFilters> by lazy {
        val initialValue = savedStateHandle.get(COUNTRY_FILTERS_KEY) ?: CountryFilters()
        MutableStateFlow(initialValue)
    }

    init {
        viewModelScope.launch {
            filters.collect {
                //TODO restore it
                //savedStateHandle.set(COUNTRY_FILTERS_KEY, it)
                countriesRepo.load(it)
            }
        }
    }

    fun getCountries(): Flow<Resource<Countries>> = countriesRepo.countries

    fun getContinents(): Flow<Resource<List<Continent>>> = countriesRepo.getContinents()

    fun getLanguages():  Flow<Resource<List<Language>>> = countriesRepo.getLanguages()

    companion object{
        const val COUNTRY_FILTERS_KEY = "COUNTRY_FILTERS_KEY"
    }
}