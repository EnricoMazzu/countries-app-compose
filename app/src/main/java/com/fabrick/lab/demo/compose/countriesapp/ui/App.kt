package com.fabrick.lab.demo.compose.countriesapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                CountriesTopAppBar(
                    appDestination = appDestination,
                    appBarState = appState.appBarState,
                    onUpNavigationClick = {
                        appState.navController.popBackStack()
                    }
                )
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                setMenuAction = { actions ->
                    appState.appBarState.setActions(actions)
                }
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
                CountriesTopAppBar(
                    appDestination = appDestination,
                    appBarState = appState.appBarState,
                    onUpNavigationClick = {
                        appState.navController.popBackStack()
                    },
                )
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                startDestination = appDestination.route,
                setMenuAction = { actions ->
                    appState.appBarState.setActions(actions)
                }
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
                CountriesTopAppBar(
                    appDestination = appDestination,
                    appBarState = appState.appBarState,
                    onUpNavigationClick = {
                        appState.navController.popBackStack()
                    })
            },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                startDestination = appDestination.route,
                setMenuAction = { actions ->
                    appState.appBarState.setActions(actions)
                }
            )
        }
    }
}





