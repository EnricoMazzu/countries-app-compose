package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries.filter

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fabrick.lab.demo.compose.countriesapp.R
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Continent
import com.fabrick.lab.demo.compose.countriesapp.domain.model.CountryFilters
import com.fabrick.lab.demo.compose.countriesapp.domain.model.Language
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.TextDropDownMenu
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.TextDropDownMenuOption
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.rememberTextDropDownMenuState
import java.util.*


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun FilterBottomSheetLayout(
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    onFilterApply: (CountryFilters) -> Unit = {},
    onFilterReset: () -> Unit = {},
    continents: List<Continent>? = Collections.emptyList(),
    languages: List<Language>? = Collections.emptyList(),
    content: @Composable () -> Unit
) {
    val continentItems = remember(continents) {
        continents?.map {
            TextDropDownMenuOption(it.code, it.name)
        }
    }

    val languageItems = remember(languages) {
        languages?.map {
            TextDropDownMenuOption(it.code, it.name)
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Surface(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(Modifier.padding(bottom = 15.dp)) {
                    FilterHeader()
                    FilterRow(
                        values = continentItems,
                        labelRes = R.string.continent_placeholder
                    )
                    Divider(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth()
                    )
                    FilterRow (
                        values = languageItems,
                        labelRes = R.string.language_placeholder
                    )
                }
            }
        },
        content = content
    )
}

@Composable
private fun FilterHeader() {
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
}

@ExperimentalMaterialApi
@Composable
fun FilterRow(
    @StringRes labelRes: Int = R.string.language_placeholder,
    values: List<TextDropDownMenuOption>? = Collections.emptyList()
) {
    val state = rememberTextDropDownMenuState()
    LaunchedEffect(values) {
        state.setItems(
            values ?: Collections.emptyList()
        )
    }
    TextDropDownMenu(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        state = state,
        labelRes = labelRes
    )
}


