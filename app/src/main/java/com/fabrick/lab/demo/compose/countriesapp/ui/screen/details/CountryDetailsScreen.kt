package com.fabrick.lab.demo.compose.countriesapp.ui.screen.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.apollographql.apollo3.api.json.BufferedSinkJsonWriter.Companion.string
import com.fabrick.lab.demo.compose.countriesapp.domain.model.CountryDetails
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.EmojiView
import com.fabrick.lab.demo.compose.countriesapp.R
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.CaptionValueItem

@Composable
fun CountryDetailsScreen (
    modifier: Modifier = Modifier,
    viewModel: CountryDetailsViewModel = hiltViewModel()
) {
    val composeState = viewModel.uiState.collectAsState(initial = CountryDetailsViewModel.UiState())
    val state = composeState.value

    state.error?.let {
        //TODO
    }
    Surface(modifier.fillMaxSize()) {
        CountryDetailsContent(
            modifier = modifier,
            onLoading = state.onLoading,
            countryDetails = state.countryDetails
        )
    }
}

@Composable
fun CountryDetailsContent(
    modifier: Modifier = Modifier,
    onLoading: Boolean = false,
    countryDetails: CountryDetails? = null
) {
    ConstraintLayout(modifier.fillMaxWidth()) {
        val (
            countryLoadProgress,
            emojiFlag,
            lblCountryName,
            lblCapital,
            lblPhone,
            lblCurrency,
            lblContinent,
            lblLanguages
        ) = createRefs()

        Column(
            Modifier.constrainAs(countryLoadProgress){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(5.dp)
            }
        ) {
            AnimatedVisibility(visible = onLoading) {
                LinearProgressIndicator(
                    Modifier.fillMaxWidth()
                )
            }
        }

        EmojiView(
            modifier = modifier
                .size(80.dp)
                .constrainAs(emojiFlag) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start, 8.dp)

                }
            ,
            textValue = countryDetails?.emoji.orEmpty()
        )

        Text(modifier = modifier
            .constrainAs(lblCountryName){
                top.linkTo(emojiFlag.top)
                start.linkTo(emojiFlag.end, 8.dp)
                end.linkTo(parent.end, 8.dp)
                width = Dimension.fillToConstraints
            },
            text = countryDetails?.name.orEmpty(),
            style = MaterialTheme.typography.h6
        )

        Text(modifier = modifier
            .constrainAs(lblContinent){
                top.linkTo(lblCountryName.bottom)
                start.linkTo(lblCountryName.start)
                end.linkTo(lblCountryName.end)
                bottom.linkTo(emojiFlag.bottom)
                width = Dimension.fillToConstraints
            },
            text = countryDetails?.continent.orEmpty(),
            style = MaterialTheme.typography.body2
        )

        CaptionValueItem (
            modifier = modifier
                .constrainAs(lblCapital) {
                    top.linkTo(emojiFlag.bottom, 24.dp)
                    start.linkTo(emojiFlag.start)
                    end.linkTo(lblContinent.end)
                    width = Dimension.fillToConstraints
                }
            ,
            label = stringResource(id = R.string.capitalLabel) ,
            value = countryDetails?.capital.orEmpty()
        )

        CaptionValueItem (
            modifier = modifier
                .constrainAs(lblPhone) {
                    top.linkTo(lblCapital.bottom, 24.dp)
                    start.linkTo(lblCapital.start)
                    end.linkTo(lblContinent.end)
                    width = Dimension.fillToConstraints
                }
            ,
            label = stringResource(id = R.string.phoneLabel) ,
            value = countryDetails?.phone.orEmpty()
        )

        CaptionValueItem (
            modifier = modifier
                .constrainAs(lblCurrency) {
                    top.linkTo(lblPhone.bottom, 24.dp)
                    start.linkTo(lblPhone.start)
                    end.linkTo(lblContinent.end)
                    width = Dimension.fillToConstraints
                }
            ,
            label = stringResource(id = R.string.currencyLabel) ,
            value = countryDetails?.currency.orEmpty()
        )

        CaptionValueItem (
            modifier = modifier
                .constrainAs(lblLanguages) {
                    top.linkTo(lblCurrency.bottom, 24.dp)
                    start.linkTo(lblCurrency.start)
                    end.linkTo(lblContinent.end)
                    width = Dimension.fillToConstraints
                }
            ,
            label = stringResource(id = R.string.languagesLabel) ,
            value = countryDetails?.languages?.joinToString(", ").orEmpty()
        )
    }
}


@Preview(name = "CountriesDetailsScreen", showBackground = true)
@Composable
fun DefaultPreview() {
    CountriesAppTheme {
        Surface(Modifier.fillMaxSize()) {
            CountryDetailsContent(
                onLoading = true,
                countryDetails = CountryDetails(
                    code = "AD",
                    name = "Andora",
                    emoji = "\uD83C\uDDE6\uD83C\uDDE9",
                    capital = "My capital",
                    phone = "+40",
                    continent = "My continent",
                    currency = "CUR",
                    languages = listOf("Gigi, Luigi, Angelo")
                )
            )
        }

    }
}
