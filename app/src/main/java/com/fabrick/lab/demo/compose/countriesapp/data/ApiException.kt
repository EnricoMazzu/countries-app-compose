package com.fabrick.lab.demo.compose.countriesapp.data

import com.apollographql.apollo3.exception.ApolloException
import com.fabrick.lab.demo.compose.countriesapp.util.ErrorResolver

enum class ErrorCode {
    GENERIC_ERROR,
    NETWORK_ERROR,
    ENCODING_EXCEPTION,
    REMOTE_SERVICE_ERROR,
    CACHE_MISS_ERROR,
}

/**
 * Represent a fail in an app api invocation
 */
open class ApiException(val code: ErrorCode, message: String?, cause: Throwable? = null): Exception(message, cause)

/**
 * Call to translate the exception as ApiException. Is safe call this function multiple time.
 */
fun Exception.asApiException(): ApiException {
    return when(this){
        is ApiException -> this
        is ApolloException -> ErrorResolver.resolveApolloException(this)
        else -> ApiException(ErrorCode.GENERIC_ERROR, this.message.orEmpty(), this)
    }
}