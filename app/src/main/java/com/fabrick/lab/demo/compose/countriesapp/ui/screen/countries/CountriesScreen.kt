package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import timber.log.Timber
import java.util.*

@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    onErrorReceived: (ex:Exception) -> Unit = {},
    onDetailsRequired: (Country) -> Unit = {}
) {
    Timber.d("Recompose CountriesScreen")
    val countriesState = viewModel.getCountries().collectAsState(initial = Resource.Loading())
    var exception: Exception? = null
    var countries = Collections.emptyList<Country>()

    when(val res = countriesState.value){
        is Resource.Loading -> {
            Timber.i("countriesState on loading")
            exception = null
        }
        is Resource.Success -> {
            Timber.i("countriesState on Success", res.data)
            countries = res.data ?: Collections.emptyList()
        }
        is Resource.Error -> {
            Timber.i("countriesState on error", res.peekException())
            exception = res.getExceptionIfNotHandled()
        }

    }

    exception?.let(onErrorReceived)

    CountriesScreenContent(
        countries = countries,
        onCountrySelected = onDetailsRequired
    )
}

@Composable
fun CountriesScreenContent(
    modifier: Modifier = Modifier,
    countries: List<Country> = Collections.emptyList(),
    onCountrySelected: (Country) -> Unit = {}
) {
    CountriesList(
        modifier = modifier,
        itemsToDraw = countries,
        onCountrySelected = onCountrySelected
    )
}


@Preview
@Composable
fun DefaultPreview(){
    CountriesScreen()
}