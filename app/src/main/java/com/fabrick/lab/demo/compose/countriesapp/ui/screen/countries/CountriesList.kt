package com.fabrick.lab.demo.compose.countriesapp.ui.screen.countries

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun CountriesList(
    modifier: Modifier
) {
}


@Composable
fun CountryItem(
    modifier: Modifier = Modifier
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = MaterialTheme.shapes.medium.copy(CornerSize(5.dp)),
        border = BorderStroke(5.dp, MaterialTheme.colors.background),
        elevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text("Hello")
            Text("Hello")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CountryItemPreview(){
    CountriesAppTheme() {
        Surface(
            Modifier
                .fillMaxSize()
                .padding(5.dp)) {
        }
        CountryItem()
    }

}