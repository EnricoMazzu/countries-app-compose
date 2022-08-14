package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabrick.lab.demo.compose.countriesapp.common.Resource
import com.fabrick.lab.demo.compose.countriesapp.data.impl.mock.MockData
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import com.fabrick.lab.demo.compose.countriesapp.ui.AppBarState
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.NavMenuAction
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.addNavMenuActions
import com.fabrick.lab.demo.compose.countriesapp.ui.rememberAppBarState
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import timber.log.Timber
import java.util.*

@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    onErrorReceived: (ex: Exception) -> Unit = {},
    onDetailsRequired: (Country) -> Unit = {},
    appBarState: AppBarState,
) {
    Timber.d("Recompose CountriesScreen")
    addNavMenuActions(
        appBarState = appBarState,
        navActions = listOf (
            NavMenuAction(
                id = 0,
                contentDescription = "Filter Toggle",
                icon = Icons.Default.FilterList,
                onActionClick = {
                    Timber.d("Open filter")
                }
            )
        )
    )

    val uiState = viewModel.uiState.collectAsState()
    val refreshState = rememberSwipeRefreshState(uiState.value.pullToRefreshLoading)
    val exception = uiState.value.error
    exception?.let(onErrorReceived)

    CountriesScreenContent(
        countries = uiState.value.countries,
        refreshState = refreshState,
        onCountrySelected = onDetailsRequired,
        onReloadRequired = {
            viewModel.reload();
        }
    )
}

@Composable
fun CountriesScreenContent(
    modifier: Modifier = Modifier,
    countries: List<Country> = Collections.emptyList(),
    refreshState: SwipeRefreshState,
    onCountrySelected: (Country) -> Unit = {},
    onReloadRequired: () -> Unit = {}
) {
    SwipeRefresh(
        state = refreshState,
        onRefresh = {
            Timber.d("Refreshing....")
            onReloadRequired()
        }
    ){
        CountriesList(
            modifier = modifier,
            itemsToDraw = countries,
            onCountrySelected = onCountrySelected
        )
    }

}


@Preview
@Composable
fun CountryScreenContentPreview(){
    CountriesAppTheme {
        Surface(Modifier.fillMaxSize()) {
            CountriesScreenContent(
                refreshState = rememberSwipeRefreshState(isRefreshing = false),
                countries = MockData.getCountries().map {
                    Country(
                        code = it.code,
                        name = it.name,
                        emoji = "\uD83C\uDDE6\uD83C\uDDE9",
                        languages = Collections.emptyList()
                    )
                }
            )
        }
    }

}