package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.common.isResourceLoaded
import com.fabrick.lab.demo.compose.countriesapp.model.Country
import java.util.*

@Composable
fun CountriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CountriesViewModel = hiltViewModel(),
    onErrorReceived: (ex:Exception) -> Unit = {}
) {

    val countriesState = viewModel.getCountries().collectAsState(initial = Resource.Loading())
    var showLoader = !countriesState.value.isResourceLoaded()
    var exception: Exception? = null
    var countries = Collections.emptyList<Country>()

    when(val res = countriesState.value){
        is Resource.Loading -> {
            exception = null
        }
        is Resource.Error -> {
            exception = res.getExceptionIfNotHandled()
        }
        is Resource.Success -> {
            countries = res.data ?: Collections.emptyList()
        }
    }

    exception?.let {
        onErrorReceived(it)
    }

    CountriesList(
        itemsToDraw = countries
    )

}

@Preview
@Composable
fun DefaultPreview(){
    CountriesScreen()
}