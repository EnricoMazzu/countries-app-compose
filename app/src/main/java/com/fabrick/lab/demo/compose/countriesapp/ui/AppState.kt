package com.fabrick.lab.demo.compose.countriesapp.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fabrick.lab.demo.compose.countriesapp.ui.navigation.NavMenuAction
import okhttp3.internal.toImmutableList
import timber.log.Timber
import java.util.*

class AppState(
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val appBarState: AppBarState
)

class AppBarState(
    initialActions: List<NavMenuAction> = Collections.emptyList()
) {
    private val _actionList: MutableState<List<NavMenuAction>> = mutableStateOf(initialActions)
    val actionList: State<List<NavMenuAction>> = _actionList

    fun setActions(actions: List<NavMenuAction>){
        _actionList.value = actions.toImmutableList()
    }

    fun clearAllActions(){
        _actionList.value = Collections.emptyList()
    }

}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    appBarState: AppBarState = rememberAppBarState()
) = remember(navController, scaffoldState, appBarState) {
    AppState(navController, scaffoldState, appBarState)
}

@Composable
fun rememberAppBarState(): AppBarState = remember {
    AppBarState()
}
