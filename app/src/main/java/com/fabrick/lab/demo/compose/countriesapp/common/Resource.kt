package com.fabrick.lab.demo.compose.countriesapp.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Represent a resource with a state (loading, success or error)
 * Resource status is immutable: a new instance is required to represent a state change
 */
sealed class Resource<out T : Any> {
    data class Loading<out T: Any>(val data: T? = null, val initial:Boolean = false): Resource<T>()
    data class Success<out T : Any>(val data: T?, val fromCache: Boolean? = false) : Resource<T>()
    data class Error<out T : Any>(private val exception: Exception, val data: T? = null) : Resource<T>() {
        var hasBeenHandled = false
            private set

        fun getExceptionIfNotHandled(): Exception?{
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                exception
            }
        }
        fun peekException() = exception;
    }
}

fun <T: Any> Resource<T>?.isResourceLoaded(): Boolean {
    //return (res is Resource.Success || res is Resource.Error)
    return this?.let { it !is Resource.Loading } ?: false
}

/**
 * TODO
 *
 * @param T
 * @param scope
 * @param onLoading
 * @param onSuccess
 * @param onError
 * @return
 */
fun <T : Any> Flow<Resource<T>>.collectResourceStates (
    scope: CoroutineScope,
    onLoading: (Resource.Loading<T>) -> Unit = {},
    onError: (Resource.Error<T>) -> Unit = {},
    onSuccess: (Resource.Success<T>) -> Unit = {},
): Job {
    return scope.launch {
        collect {
            when(it){
                is Resource.Loading -> onLoading(it)
                is Resource.Success -> onSuccess(it)
                is Resource.Error -> onError(it)
            }
        }
    }
}