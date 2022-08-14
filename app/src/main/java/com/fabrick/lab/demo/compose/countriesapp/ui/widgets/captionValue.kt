package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun LabelValueItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    labelStyle: TextStyle = MaterialTheme.typography.body2,
    valueStyle: TextStyle = MaterialTheme.typography.body1,
){
    Column(modifier = modifier.fillMaxWidth()) {
        //label
        Text(
            modifier = modifier,
            text = label,
            style = labelStyle.copy(fontStyle = FontStyle.Italic)
        )
        //value
        Text(
            modifier = modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
            ,
            text = value,
            style = valueStyle
        )
    }
}
