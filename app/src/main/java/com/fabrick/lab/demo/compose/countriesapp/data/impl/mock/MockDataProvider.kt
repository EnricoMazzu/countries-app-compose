package com.fabrick.lab.demo.compose.countriesapp.data.impl.mock


import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.data.DataProvider
import com.fabrick.lab.demo.compose.countriesapp.model.*
import kotlinx.coroutines.delay

class MockDataProvider : DataProvider {

    override suspend fun getCountries(
        filter: CountryFilters?,
        useNetwork: Boolean?
    ): Resource<Countries> {
        delay(1000)
        val countries = MockData.getCountries();
        val result = countries.filter {
            filter.isNullOrEmpty() || matchFilter(filter!!.continent, filter.language,it)
        }.map {
            Country(
                name = it.name,
                code = it.code,
                emoji = it.emoji,
                languages = MockData.getLanguages().filter {l ->
                    l.name in it.languages
                }
            )
        }
        return Resource.Success(result)
    }

    private fun matchFilter(continent: String?, language: String?, details: CountryDetails): Boolean {
        var included = true;
        if(!continent.isNullOrEmpty()){
            included = details.continent == continent
        }
        if(!language.isNullOrEmpty()){
            included = included.and(language in details.languages)
        }
        return included
    }

    override suspend fun getCountryDetails(code: String): Resource<CountryDetails> {
        delay(1000)
        return Resource.Success(MockData.getCountries().findLast {
            it.code == code
        }!!)
    }

    override suspend fun getContinents(): Resource<List<Continent>> {
        delay(1000)
        return Resource.Success(MockData.getContinent())
    }

    override suspend fun getLanguages(): Resource<List<Language>> {
        delay(1000)
        return Resource.Success(MockData.getLanguages())
    }

}
