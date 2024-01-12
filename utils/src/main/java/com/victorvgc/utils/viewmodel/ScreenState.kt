package com.victorvgc.utils.viewmodel

sealed class ScreenState<T> {
    data class Loading<T>(val preLoadedData: T? = null) : ScreenState<T>()
    data class Error<T>(val code: Int, val data: T? = null) : ScreenState<T>()
    data class Success<T>(val data: T) : ScreenState<T>()
}
