package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiView(
    modifier: Modifier = Modifier,
    textValue: String,
){
    Surface(
        modifier = modifier
    ) {
        Text(text = textValue, fontSize = 40.sp, textAlign = TextAlign.Center)
    }

}