package com.fabrick.lab.demo.compose.countriesapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
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
    val requireUpNavigation: Boolean
}

object CountriesScreen: AppDestination {
    override val route = "countries"
    @StringRes
    override val title = R.string.countries_title
    override val requireUpNavigation = false
}

object CountryDetails: AppDestination {
    private const val baseRoute = "country"
    const val CountryIdParams = "countryId"
    override val route = "$baseRoute/{$CountryIdParams}"
    @StringRes
    override val title = R.string.country_detail_title
    override val requireUpNavigation = true

    fun createRoute(countryId: String) = "$baseRoute/$countryId"
}

var AllRoutes: List<AppDestination>  = listOf(CountriesScreen, CountryDetails)


fun resolveAppDestination(currentBackStack: NavBackStackEntry?): AppDestination {
    return AllRoutes.find {
        it.route == currentBackStack?.destination?.route
    } ?: CountriesScreen
}

fun upNavigationRequired(appDestination: AppDestination): Boolean {
    return appDestination.requireUpNavigation
}






