package com.fabrick.lab.demo.compose.countriesapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.CountriesScreen
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.CountriesViewModel
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.details.CountryDetailsScreen
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.details.CountryDetailsViewModel
import timber.log.Timber

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = CountriesScreen.route
) {
    val goToDetails = { it: Country -> navController.navigate(CountryDetails.createRoute(it.code)) }
    val callbackInMemory = remember {
        goToDetails
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ){
        Timber.d("[Recompose] NavHost")
        // Screen of Countries
        composable(
            route = CountriesScreen.route
        ){
            val viewModel: CountriesViewModel = hiltViewModel()
            CountriesScreen(
                viewModel = viewModel,
                onDetailsRequired = callbackInMemory/*{
                    navController.navigate(CountryDetails.createRoute(it.code))
                }*/
            )
        }
        // Screen of country details
        composable(
            route = CountryDetails.route,
            arguments = listOf(
                navArgument(CountryDetails.CountryIdParams){
                    nullable = false
                    type = NavType.StringType
                }
            )){
            val countryId = it.arguments?.getString(CountryDetails.CountryIdParams)
                ?: NavigationConstants.NoId
            val viewModel: CountryDetailsViewModel = hiltViewModel()
            viewModel.setCountryCode(countryId)
            CountryDetailsScreen(
                viewModel = viewModel
            )
        }
    }
}

