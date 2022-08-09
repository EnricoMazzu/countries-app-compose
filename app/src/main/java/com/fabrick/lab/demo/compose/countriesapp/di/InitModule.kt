package com.fabrick.lab.demo.compose.countriesapp.di

import com.fabrick.lab.demo.compose.countriesapp.initialize.AppInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InitModule {

    @Provides
    @Singleton
    fun provideAppInitializer(): AppInitializer {
        return AppInitializer()
    }

}