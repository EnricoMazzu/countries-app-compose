package com.fabrick.lab.demo.compose.countriesapp.data.impl

import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.data.CountriesRepo
import com.fabrick.lab.demo.compose.countriesapp.data.DataProvider
import com.fabrick.lab.demo.compose.countriesapp.model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class CountriesRepoImpl (
    private val dataProvider: DataProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CountriesRepo {

    private val _countries: MutableStateFlow<Resource<Countries>> by lazy {
        MutableStateFlow(Resource.Loading(initial = true))
    }
    override val countries: Flow<Resource<Countries>> = _countries

    override suspend fun load(countryFilters: CountryFilters?, forceNetworkFetch: Boolean){
        loadCountriesInternal(
            countryFilters = countryFilters,
            useNetwork = forceNetworkFetch
        )
    }

    private suspend fun loadCountriesInternal(countryFilters: CountryFilters? = null, useNetwork: Boolean) {
        Timber.d("loadCountriesInternal useNetwork: %s", useNetwork)
        _countries.emit(Resource.Loading())
        val resource = dataProvider.getCountries(
            filter = countryFilters,
            useNetwork = useNetwork
        );
        _countries.emit(resource)
    }

    override fun getCountryDetails(code:String): Flow<Resource<CountryDetails>> {
        return flow {
            emit(Resource.Loading())
            val res = dataProvider.getCountryDetails(code)
            emit(res)
        }
    }

    override fun getLanguages(): Flow<Resource<List<Language>>>{
        return flow {
            emit(Resource.Loading())
            val resource = dataProvider.getLanguages();
            emit(resource)
        }
    }

    override fun getContinents(): Flow<Resource<List<Continent>>>{
        return flow {
            emit(Resource.Loading())
            val resource = dataProvider.getContinents();
            emit(resource)
        }
    }

}