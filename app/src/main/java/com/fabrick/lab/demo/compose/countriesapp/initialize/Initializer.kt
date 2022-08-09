package com.fabrick.lab.demo.compose.countriesapp.initialize

import timber.log.Timber

class Initializer {

    fun initialize(){
        Timber.plant(Timber.DebugTree())
        Timber.d("initialize")
    }
}