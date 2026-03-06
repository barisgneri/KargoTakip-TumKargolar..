package com.barisproduction.kargo.data.util

import com.barisproduction.kargo.common.AppError
import com.barisproduction.kargo.common.Resource
import io.ktor.client.plugins.ResponseException
import kotlinx.io.IOException

object ErrorParser {
    fun <T> parse(e: Throwable): Resource.Error<T> {
        val error = when (e) {
            is IOException -> AppError.NetworkConnection
            is ResponseException -> {
                when (e.response.status.value) {
                    401 -> AppError.Unauthorized
                    404 -> AppError.NotFound
                    in 500..599 -> AppError.ServerUnavailable
                    else -> AppError.General("HTTP Hata Kodu: ${e.response.status.value}")
                }
            }
            else -> AppError.General(e.message ?: "Beklenmedik bir hata oluştu")
        }

        return Resource.Error(error)
    }

    fun <T> parseWebViewError(errorCode: Int): Resource.Error<T> {
        val error = when (errorCode) {
            -2, -5 -> AppError.NetworkConnection // ERROR_HOST_LOOKUP veya ERROR_CONNECT
            -8 -> AppError.ServerUnavailable    // ERROR_TIMEOUT
            404 -> AppError.NotFound
            else -> AppError.WebViewError(errorCode)
        }

        return Resource.Error(errorType = error,)
    }
}
