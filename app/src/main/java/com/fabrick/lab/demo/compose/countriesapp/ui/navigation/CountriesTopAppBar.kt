package com.fabrick.lab.demo.compose.countriesapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.fabrick.lab.demo.compose.countriesapp.ui.AppBarState
import timber.log.Timber
import java.util.*

@Composable
fun CountriesTopAppBar(
    appDestination: AppDestination,
    appBarState: AppBarState,
    onUpNavigationClick: () -> Unit = {},
){
    val showUpNavigationBack = upNavigationRequired(appDestination)
    val actionsState = appBarState.actionList
    Timber.d("Recompose appBar: ${actionsState.value}")
    TopAppBar(
        title = { Text(stringResource(id = appDestination.title)) },
        navigationIcon = if(showUpNavigationBack){
            {
                IconButton(onClick = onUpNavigationClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "BackIcon",
                    )
                }
            }
        } else null ,
        actions = {
            Timber.i("Render actions: ${actionsState.value}")
            actionsState.value.onEach {
                IconButton(onClick = it.onActionClick) {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.contentDescription,
                        tint = LocalContentColor.current
                    )
                }
            }
        },
    )
}

@Composable
@SuppressLint("ComposableNaming")
fun addNavMenuActions (
    setMenuActions: (List<NavMenuAction>) -> Unit,
    navActions: List<NavMenuAction> = Collections.emptyList(),
) {
    DisposableEffect(true){
        setMenuActions(navActions)
        onDispose {
            Timber.d("Dispose effect: clear menu action")
            setMenuActions(Collections.emptyList())
        }
    }
}

data class NavMenuAction (
    val id: Int,
    val contentDescription: String,
    val icon: ImageVector,
    val onActionClick: () -> Unit
)

