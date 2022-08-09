package com.fabrick.lab.demo.compose.countriesapp.ui.screen.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(): ViewModel() {

    fun setCountryId(countryId: String) {
        Timber.d("countryId $countryId")
    }

}