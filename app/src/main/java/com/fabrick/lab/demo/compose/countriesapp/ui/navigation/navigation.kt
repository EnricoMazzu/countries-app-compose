package com.fabrick.lab.demo.compose.countriesapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fabrick.lab.demo.compose.countriesapp.R
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.CountriesScreen
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.CountriesViewModel
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.details.CountryDetailsScreen
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.details.CountryDetailsViewModel

object NavigationConstants {
    const val NoId = "NO_ID"
}

interface AppDestination {
    val route: String
    val title: Int
}

object CountriesScreen: AppDestination {
    override val route = "countries"
    @StringRes
    override val title = R.string.countries_title
}

object CountryDetails: AppDestination {
    private const val baseRoute = "country"
    const val CountryIdParams = "countryId"
    override val route = "$baseRoute/{$CountryIdParams}"
    @StringRes
    override val title = R.string.country_detail_title

    fun createRoute(countryId: String) = "$baseRoute/$countryId"
}

var AllRoutes: List<AppDestination>  = listOf(CountriesScreen, CountryDetails)




