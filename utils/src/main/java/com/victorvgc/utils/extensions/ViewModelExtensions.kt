package com.victorvgc.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun ViewModel.execute(block: suspend () -> Unit) {
    viewModelScope.launch {
        block()
    }
}

fun <T> ViewModel.async(block: suspend () -> T): Deferred<T> {
    return viewModelScope.async {
        block()
    }
}
