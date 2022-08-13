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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme
import com.fabrick.lab.demo.compose.countriesapp.ui.widgets.EmojiView

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
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()) {
            val (emoji, name, code) = createRefs()

            EmojiView(
                textValue = flagEmoji,
                modifier = modifier
                    .size(60.dp)
                    .constrainAs(emoji){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, 4.dp)
                }
            )

            Text(
                modifier = modifier.constrainAs(name){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(emoji.end, 8.dp)
                },
                text = countryName,
                style = MaterialTheme.typography.h6
            )

            Text(
                modifier = modifier.constrainAs(code){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 8.dp)
                },
                text = countryCode,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

/*@Composable
fun EmojiView(
    modifier: Modifier = Modifier,
    textValue: String
){
    Surface(
        modifier = modifier.size(60.dp)) {
        Text(text = textValue, fontSize = 40.sp, textAlign = TextAlign.Center)
    }

}*/

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