package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CaptionValueItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String
){
    Column(modifier = modifier.fillMaxWidth()) {
        //label
        Text(
            modifier = modifier,
            text = label,
            style = MaterialTheme.typography.body2
        )
        //value
        Text(
            modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
            ,
            text = value,
            style = MaterialTheme.typography.body1
        )
    }
}
