package com.fabrick.lab.demo.compose.countriesapp.ui.screen.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun CountryDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    Surface(modifier.fillMaxSize()) {
        Text(text = "CountriesDetailsScreen")
    }
}

@Preview(name = "CountriesDetailsScreen")
@Composable
fun DefaultPreview() {
    CountriesAppTheme {
        CountryDetailsScreen()
    }
}
