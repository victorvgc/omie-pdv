package com.victorvgc.utils.viewmodel

import androidx.lifecycle.ViewModel
import com.victorvgc.utils.extensions.execute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<StateData, EventType> : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<StateData>>(ScreenState.Loading())

    val screenState: StateFlow<ScreenState<StateData>> = _screenState.asStateFlow()

    protected fun showError(code: Int = -1, data: StateData? = null) {
        execute {
            _screenState.emit(ScreenState.Error(code, data))
        }
    }

    protected fun showSuccess(data: StateData) {
        execute {
            _screenState.emit(ScreenState.Success(data))
        }
    }

    protected fun showLoading(preLoadedData: StateData? = null) {
        execute {
            _screenState.emit(ScreenState.Loading(preLoadedData))
        }
    }

    fun sendEvent(event: EventType) {
        onScreenEvent(event)
    }

    protected abstract fun onScreenEvent(event: EventType)
}
