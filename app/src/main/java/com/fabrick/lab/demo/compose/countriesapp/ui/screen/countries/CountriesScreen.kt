package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabrick.lab.demo.compose.countriesapp.data.impl.mock.MockData
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Country
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.NavMenuAction
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.addNavMenuActions
import com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.filter.FilterBottomSheetLayout
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    onErrorReceived: (ex: Exception) -> Unit = {},
    onDetailsRequired: (Country) -> Unit = {},
    setMenuActions: (List<NavMenuAction>) -> Unit = {},
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    Timber.d("Recompose CountriesScreen")
    addNavMenuActions(
        setMenuActions = setMenuActions,
        navActions = {
            listOf (
                NavMenuAction(
                    id = 0,
                    contentDescription = "Filter Toggle",
                    icon = Icons.Default.FilterList,
                    onActionClick = {
                        coroutineScope.launch {
                            if(modalBottomSheetState.isVisible){
                                modalBottomSheetState.hide()
                            }else{
                                modalBottomSheetState.show()
                            }

                        }
                    }
                )
            )
        }
    )

    val uiState = viewModel.uiState.collectAsState()
    val refreshState = rememberSwipeRefreshState(uiState.value.pullToRefreshLoading)
    val exception = uiState.value.error
    exception?.let(onErrorReceived)

    val uiFilterState by viewModel.uiFilterState.collectAsState()

    FilterBottomSheetLayout(
        sheetState = modalBottomSheetState,
        continents = uiFilterState.continents,
        languages = uiFilterState.languages
    ) {
        CountriesScreenContent(
            countries = uiState.value.countries,
            refreshState = refreshState,
            showProgress = uiState.value.loading,
            onCountrySelected = onDetailsRequired,
            onReloadRequired = {
                viewModel.reload();
            }
        )
    }
}

@Composable
fun CountriesScreenContent(
    modifier: Modifier = Modifier,
    countries: List<Country> = Collections.emptyList(),
    showProgress: Boolean,
    refreshState: SwipeRefreshState,
    onCountrySelected: (Country) -> Unit = {},
    onReloadRequired: () -> Unit = {},
) {
    // use box to allows overlapping. In case
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = showProgress) {
            LinearProgressIndicator(
                Modifier.fillMaxWidth()
            )
        }
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                Timber.d("Refreshing....")
                onReloadRequired()
            }
        ){
            CountriesList(
                modifier = Modifier,
                itemsToDraw = countries,
                onCountrySelected = onCountrySelected
            )
        }
    }


}


@Preview
@Composable
fun CountryScreenContentPreview(){
    CountriesAppTheme {
        Surface(Modifier.fillMaxSize()) {
            CountriesScreenContent(
                countries = MockData.getCountries().map {
                    Country(
                        code = it.code,
                        name = it.name,
                        emoji = "\uD83C\uDDE6\uD83C\uDDE9",
                        languages = Collections.emptyList()
                    )
                },
                refreshState = rememberSwipeRefreshState(isRefreshing = false),
                showProgress = false
            )
        }
    }

}