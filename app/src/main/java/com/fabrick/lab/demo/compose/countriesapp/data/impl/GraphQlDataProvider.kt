package com.fabrick.lab.demo.compose.countriesapp.data.impl

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.cache.normalized.isFromCache
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.data.ApiException
import com.fabrick.lab.demo.compose.countriesapp.data.DataProvider
import com.fabrick.lab.demo.compose.countriesapp.data.ErrorCode
import com.fabrick.lab.demo.compose.countriesapp.data.asApiException
import com.fabrick.lab.demo.compose.countriesapp.graphql.ContinentsQuery
import com.fabrick.lab.demo.compose.countriesapp.graphql.CountryDetailsQuery
import com.fabrick.lab.demo.compose.countriesapp.graphql.FilteredCountriesQuery
import com.fabrick.lab.demo.compose.countriesapp.graphql.LanguagesQuery
import com.fabrick.lab.demo.compose.countriesapp.model.*
import timber.log.Timber

class GraphQlDataProvider(
    private val apolloClient: ApolloClient
) : DataProvider {

    override suspend fun getCountries(
        filter: CountryFilters?,
        useNetwork: Boolean?
    ): Resource<Countries> {
        val filterValue = filter?.continent?.let {
            Optional.Present(it)
        } ?: Optional.Absent
        val query = FilteredCountriesQuery(filterValue)
        val originalResource:Resource<Countries> = fetchCountries(query) {
            it.mapToModel()
        }
        return applyClientSideFilter(originalResource, filter?.language)
    }

    private suspend fun fetchCountries(
        query: FilteredCountriesQuery,
        mapper: (FilteredCountriesQuery.Data) -> Countries
    ): Resource<List<Country>> {
        return try {
            val call = apolloClient.query(query)
            val result = call.execute()  //call.executeCacheAndNetwork().single()
            when (result.hasErrors()) {
                false -> Resource.Success(mapper.invoke(result.data!!), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        }catch (ex: Exception){
            Resource.Error(ex.asApiException())
        }
    }

    private fun applyClientSideFilter(resource: Resource<Countries>, language: String?): Resource<Countries> {
        if(resource is Resource.Success && !language.isNullOrEmpty()){
            return Resource.Success(
                resource.data?.filter{
                    it.languages.indexOfFirst { l -> l.code == language } >= 0
                }?: ArrayList()
                , resource.fromCache)
        }
        return resource;
    }

    override suspend fun getCountryDetails(code: String): Resource<CountryDetails> {
        return getCountryDetailsResource(code)
    }

    private suspend fun getCountryDetailsResource(code: String): Resource<CountryDetails> {
        return try {
            val call = apolloClient.query(CountryDetailsQuery(code))
            val result = call.execute()
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex.asApiException());
        }
    }

    override suspend fun getContinents(): Resource<List<Continent>> {
        return try {
            val call = apolloClient.query(ContinentsQuery())
            val result = call.execute()
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex.asApiException())
        }
    }

    override suspend fun getLanguages(): Resource<List<Language>> {
        return getLanguagesResource();
    }

    private suspend fun getLanguagesResource(): Resource<List<Language>> {
        return try {
            val call = apolloClient.query(LanguagesQuery())
            val result = call.execute()
            result.data
            when (result.hasErrors()) {
                false -> Resource.Success(result.data.mapToModel(), result.isFromCache)
                else -> Resource.Error(createError(result, result.errors!!))
            }
        } catch (ex: Exception) {
            Resource.Error(ex.asApiException())
        }
    }

    private fun <D: Query.Data> createError(result: ApolloResponse<D>, errors: List<Error>): Exception {
        Timber.d("createError result = %s; errors = %s", result, errors)
        return ApiException(ErrorCode.REMOTE_SERVICE_ERROR, "Remote service error")
    }

}