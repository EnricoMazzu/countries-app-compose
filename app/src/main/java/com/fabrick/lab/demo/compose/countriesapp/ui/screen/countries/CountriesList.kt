package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import java.util.*

@Composable
fun CountriesList (
    modifier: Modifier = Modifier,
    itemsToDraw: List<Country> = Collections.emptyList()
) {
    LazyColumn(modifier) {
        items(
            items = itemsToDraw,
            key = { country: Country ->
                country.code
            }
        ) { country  ->
            CountryItem (
                flagEmoji = country.emoji,
                countryName = country.name,
                countryCode = country.code
            )
        }
    }
}