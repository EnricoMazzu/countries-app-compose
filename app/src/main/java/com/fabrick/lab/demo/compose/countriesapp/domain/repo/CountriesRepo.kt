package com.fabrick.lab.demo.compose.countriesapp.domain.repo

import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CountriesRepo {

    val countries: Flow<Resource<Countries>>

    suspend fun load(countryFilters: CountryFilters? = null, forceNetworkFetch: Boolean = false)

    fun getCountryDetails(code:String): Flow<Resource<CountryDetails>>

    fun getLanguages(): Flow<Resource<List<Language>>>

    fun getContinents(): Flow<Resource<List<Continent>>>

}