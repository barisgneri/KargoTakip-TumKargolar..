package com.barisproduction.kargo.common

sealed class Resource<T>(val data: T? = null) {

    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(
        val errorType: AppError,
    ) : Resource<T>(data = null)

    class Loading<T>(data: T? = null) : Resource<T>(data = data)
}

sealed class AppError {
    data object NetworkConnection : AppError()
    data object ServerUnavailable : AppError()
    data object NotFound : AppError()
    data object Unauthorized : AppError()
    data class General(val message: String) : AppError()
    data object InvalidTrackingNumber : AppError()
    data object TrackingServiceDown : AppError()
    data class WebViewError(val errorCode: Int) : AppError()
}