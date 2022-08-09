package com.fabrick.lab.demo.compose.countriesapp

import android.app.Application
import com.fabrick.lab.demo.compose.countriesapp.initialize.Initializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CountriesApplication: Application() {

    @Inject
    lateinit var initializer: Initializer

    override fun onCreate() {
        super.onCreate()
        initializer.initialize()
    }

}