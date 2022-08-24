package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fabrick.lab.demo.compose.countriesapp.R
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.CountriesTopAppBar
import com.fabrick.lab.demo.compose.countriesapp.ui.rememberAppBarState
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.TextDropDownMenu
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.TextDropDownMenuOption
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.TextDropDownMenuState
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.rememberTextDropDownMenuState
import timber.log.Timber


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun FilterBottomSheetLayout(
    viewModel: CountriesViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    content: @Composable () -> Unit
) {
    Timber.d("View Model retrieve: ${viewModel.hashCode()}")
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {
                val continentMenuState: TextDropDownMenuState = rememberTextDropDownMenuState()
                val languageMenuState: TextDropDownMenuState = rememberTextDropDownMenuState()

                Column(Modifier.padding(bottom = 15.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(bottom = 10.dp)
                            .background(MaterialTheme.colors.primary),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 10.dp),
                            text = stringResource(R.string.filter_header_label),
                            style = MaterialTheme.typography.h6,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                    LaunchedEffect(key1 = sheetState.isVisible){
                        if(sheetState.isVisible){
                            languageMenuState.setItems(listOf(
                                TextDropDownMenuOption("1", "First Item"),
                                TextDropDownMenuOption("2", "Second Item"),
                                TextDropDownMenuOption("3", "Third Item")
                            ))
                        }
                    }
                    TextDropDownMenu(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        state = languageMenuState,
                        labelRes = R.string.continent_placeholder
                    )
                    Divider(
                        Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    )
                    LaunchedEffect(key1 = sheetState.isVisible){
                        if(sheetState.isVisible){
                            continentMenuState.setItems(listOf(
                                TextDropDownMenuOption("1", "First Item"),
                                TextDropDownMenuOption("2", "Second Item"),
                                TextDropDownMenuOption("3", "Third Item")
                            ))
                        }
                    }

                    TextDropDownMenu(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        state = continentMenuState,
                        labelRes = R.string.language_placeholder
                    )
                }

            }
        },
        content = content
    )
}
