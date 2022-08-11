package com.fabrick.lab.demo.compose.countriesapp.di

import com.apollographql.apollo3.ApolloClient
import com.fabrick.lab.demo.compose.countriesapp.data.DataProvider
import com.fabrick.lab.demo.compose.countriesapp.data.impl.GraphQlDataProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataProvidersModule {

    @Provides
    @Singleton
    fun provideDataProvider(client: ApolloClient): DataProvider {
        return GraphQlDataProvider(client)
    }
}