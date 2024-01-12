package com.victorvgc.utils.result_helper

import com.victorvgc.utils.failures.FailureCodes

sealed class ResultWrapper<T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Failure<T>(val error: FailureCodes) : ResultWrapper<T>()

    val isSuccess: Boolean
        get() = this is Success

    suspend fun <R> onSuccess(block: suspend (T) -> R): ResultWrapper<T> {
        if (this is Success)
            block(this.data)

        return this
    }

    suspend fun <R> onFailure(block: suspend (FailureCodes) -> R): ResultWrapper<T> {
        if (this is Failure)
            block(this.error)

        return this
    }

    fun toSuccess(): Success<T>? = this as? Success<T>
    fun toFailure(): Failure<T>? = this as? Failure<T>
}

fun <T> T.toSuccess(): ResultWrapper.Success<T> = ResultWrapper.Success(data = this)
