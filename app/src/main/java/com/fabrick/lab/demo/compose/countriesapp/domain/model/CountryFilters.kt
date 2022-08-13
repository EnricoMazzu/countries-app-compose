package com.fabrick.lab.demo.compose.countriesapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryFilters(val continent: String? = null, val language:String? = null): Parcelable {
    fun isEmpty() = continent.isNullOrEmpty() && language.isNullOrEmpty()
}


fun CountryFilters?.isNullOrEmpty(): Boolean = this?.isEmpty() ?: true
