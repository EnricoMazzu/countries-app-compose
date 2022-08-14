package com.fabrick.lab.demo.compose.countriesapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
                    onUpNavigationClick = {
                        appState.navController.popBackStack()
                    }
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

@Composable
fun CountriesTopAppBar(
    appDestination: AppDestination,
    onUpNavigationClick: () -> Unit = {}
){
    val showUpNavigationBack = upNavigationRequired(appDestination)
    TopAppBar(
        title = { Text(stringResource(id = appDestination.title)) },
        navigationIcon = if(showUpNavigationBack){
            {
                IconButton(onClick = onUpNavigationClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "BackIcon",
                    )
                }
            }
        } else null ,
        actions = {},
    )
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
            topBar = { CountriesTopAppBar(appDestination = appDestination) },
            scaffoldState = appState.scaffoldState) {
            AppNavHost (
                Modifier.padding(it),
                navController = appState.navController,
                startDestination = appDestination.route
            )
        }
    }
}





