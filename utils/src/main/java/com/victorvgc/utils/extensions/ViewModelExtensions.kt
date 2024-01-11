package com.victorvgc.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.execute(block: suspend () -> Unit) {
    viewModelScope.launch {
        block()
    }
}
