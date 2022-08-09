package com.fabrick.lab.demo.compose.countriesapp.model

data class CountryFilters(val continent: String? = null, val language:String? = null) {
    fun isEmpty() = continent.isNullOrEmpty() && language.isNullOrEmpty()
}


fun CountryFilters?.isNullOrEmpty(): Boolean = this?.isEmpty() ?: true
