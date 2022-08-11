package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    flagEmoji: String,
    countryName : String,
    countryCode: String,
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(5.dp),
        shape = MaterialTheme
            .shapes
            .medium
            .copy(CornerSize(5.dp)),
        border = BorderStroke(5.dp, MaterialTheme.colors.background),
        elevation = 3.dp
    ) {
        Row(
            modifier = modifier.padding(5.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            //Flag
            Column(
                modifier =  Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                EmojiView(
                    textValue = flagEmoji
                )
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //Country
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = countryName,
                            style = MaterialTheme.typography.h6
                        )
                    }

                    //Caption
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = countryCode,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmojiView(
    modifier: Modifier = Modifier,
    textValue: String
){
    Surface(
        modifier = modifier.size(60.dp))
    {
        Text(text = textValue, fontSize = 40.sp)
    }

}

@Preview(showBackground = true)
@Composable
fun CountryItemPreview(){
    CountriesAppTheme {
        Surface(
            Modifier.fillMaxWidth()) {
            CountryItem(
                flagEmoji = "\uD83C\uDDE6\uD83C\uDDE9",
                countryName = "Andora",
                countryCode = "AD"
            )
        }

    }
}