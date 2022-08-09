package com.fabrick.lab.demo.compose.countriesapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.*
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun App(){
    CountriesAppTheme {
        val appState = rememberAppState()
        val currentBackStack by appState.navController.currentBackStackEntryAsState()
        val appDestination = resolveAppDestination(currentBackStack)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = appDestination.title)) },
                    actions = {},
                )
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController
            )
        }

    }
}

@Preview("AppWithCountriesPreview", showBackground = true)
@Composable
fun AppWithCountriesPreview(){
    val appState = rememberAppState()
    val appDestination = CountriesScreen
    CountriesAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = appDestination.title)) },
                    actions = {},
                )
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                startDestination = appDestination.route
            )
        }
    }
}

@Preview("AppWithDetails", showBackground = true)
@Composable
fun AppWithDetailsPreview(){
    val appState = rememberAppState()
    val appDestination = CountryDetails
    CountriesAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = appDestination.title)) },
                    actions = {},
                )
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                startDestination = appDestination.route
            )
        }
    }
}



fun resolveAppDestination(currentBackStack: NavBackStackEntry?): AppDestination {
    return AllRoutes.find {
        it.route == currentBackStack?.destination?.route
    } ?: CountriesScreen
}
