package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fabrick.lab.demo.compose.countriesapp.data.impl.mock.MockData
import com.fabrick.lab.demo.compose.countriesapp.data.impl.mock.MockDataProvider
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Language
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme
import java.util.*

@Composable
fun CountriesList (
    modifier: Modifier = Modifier,
    itemsToDraw: List<Country> = Collections.emptyList(),
    onCountrySelected: (Country) -> Unit = {}
) {
    LazyColumn(modifier) {
        items(
            items = itemsToDraw,
            key = { country: Country ->
                country.code
            }
        ) { country  ->
            CountryItem (
                country = country,
                onCountrySelected = {
                    onCountrySelected(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountriesListPreview(){
    val items = mutableListOf<Country>()
    for (i in 1..20){
        items.add(
            Country(
                code = "C$i",
                name = "Country $i",
                emoji = "\uD83C\uDDE6\uD83C\uDDE9", //it.emoji,
                languages = Collections.emptyList()
            )
        )
    }
    val list = remember { items }

    CountriesAppTheme {
        Surface(Modifier.fillMaxSize()) {
            CountriesList(
                itemsToDraw = list
            )
        }
    }
}

