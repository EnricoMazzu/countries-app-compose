package com.fabrick.lab.demo.compose.countriesapp.common

import com.fabrick.lab.demo.compose.countriesapp.common.Resource.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T : Any> Flow<Resource<T>>.collectResourceStates (
    scope: CoroutineScope,
    loading: (Resource.Loading<T>) -> Unit = {},
    success: (Resource.Success<T>) -> Unit = {},
    error: (Resource.Error<T>) -> Unit = {}
): Job {
    return scope.launch {
        collect {
            when(it){
                is Loading -> loading(it)
                is Resource.Success -> success(it)
                is Resource.Error -> error(error)
            }
        }
    }
}