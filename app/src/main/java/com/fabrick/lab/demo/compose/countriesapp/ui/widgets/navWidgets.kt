package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NavBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick =  onClick) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "BackIcon",
        )
    }
}

@Preview
@Composable
fun DefaultPreview(){
    TopAppBar(
        title = { Text("Example") },
        navigationIcon = {
            NavBackButton()
        }
    )
}