package com.fabrick.lab.demo.compose.countriesapp.di

import com.fabrick.lab.demo.compose.countriesapp.data.DataProvider
import com.fabrick.lab.demo.compose.countriesapp.data.impl.CountriesRepoImpl
import com.fabrick.lab.demo.compose.countriesapp.domain.repo.CountriesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideCountriesRepo(provider: DataProvider): CountriesRepo {
        return CountriesRepoImpl(provider)
    }
}