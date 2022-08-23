package com.fabrick.lab.demo.compose.countriesapp.ui.widgets

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fabrick.lab.demo.compose.countriesapp.R
import java.util.*

data class TextDropDownMenuOption(
    val id:String,
    val text:String
)

class TextDropDownMenuState{
    private val _items: MutableState<List<TextDropDownMenuOption>> = mutableStateOf(Collections.emptyList())
    val item: State<List<TextDropDownMenuOption>> = _items

    private val _expanded = mutableStateOf(false)
    val expanded: State<Boolean> = _expanded

    private var _selectedOption: MutableState<TextDropDownMenuOption?> = mutableStateOf(item.value.getOrNull(0))
    var selectedOption: State<TextDropDownMenuOption?> = _selectedOption


    fun setItems(values: List<TextDropDownMenuOption>){
        this._items.value = values
    }

    fun setExpanded(value: Boolean){
        _expanded.value = value;
    }

    fun toggleExpanded(){
        _expanded.value = !_expanded.value
    }

    fun select(target: TextDropDownMenuOption){
        _selectedOption.value = target
    }

}

@Composable
fun rememberTextDropDownMenuState(parentId: String = "parent") = remember(parentId) {
    TextDropDownMenuState()
}

@ExperimentalMaterialApi
@Composable
fun TextDropDownMenu(
    modifier: Modifier = Modifier,
    state: TextDropDownMenuState,
    onMenuExpanded: () -> Unit = {}
){
    val options by state.item
    val expanded by state.expanded
    val selectedOptionText by state.selectedOption

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            state.toggleExpanded()
        }
    ) {
        OutlinedTextField(
            modifier = modifier,
            readOnly = true,
            value = selectedOptionText?.text.orEmpty(),
            onValueChange = { },
            label = {
                Text(stringResource(id = R.string.continent_placeholder))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.continent_placeholder))
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            //colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                state.setExpanded(false)
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        state.select(selectionOption)
                        state.setExpanded(false)
                    }
                ) {
                    Text(text = selectionOption.text)
                }
            }
        }
    }
}

