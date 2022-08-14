package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fabrick.lab.demo.compose.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun EmojiView(
    modifier: Modifier = Modifier,
    textValue: String,
    fontSize: TextUnit = 40.sp
){
    Surface(
        modifier = modifier
    ) {
        Text(text = textValue, fontSize = fontSize, textAlign = TextAlign.Center)
    }

}

@Preview(showBackground = true)
@Composable
fun EmojiViewPreview(){
    val textValue = "\uD83C\uDDE6\uD83C\uDDE9"
    CountriesAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Column {
                EmojiView(textValue = textValue)

            }

        }
    }
}