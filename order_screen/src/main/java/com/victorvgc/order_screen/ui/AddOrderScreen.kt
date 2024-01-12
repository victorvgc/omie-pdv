package com.victorvgc.order_screen.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.victorvgc.design_system.ui.screens.ErrorScreen
import com.victorvgc.design_system.ui.screens.LoadingScreen
import com.victorvgc.order_screen.domain.models.OrderScreenState
import com.victorvgc.order_screen.viewmodel.OrderScreenViewModel
import com.victorvgc.utils.viewmodel.ScreenState
import com.victorvgc.design_system.R as designSystemR

@Composable
fun AddOrderScreen(
    orderScreenViewModel: OrderScreenViewModel,
    onBackClick: () -> Unit
) {
    val screenState: ScreenState<OrderScreenState> by orderScreenViewModel.screenState.collectAsState()

    when (val state = screenState) {
        is ScreenState.Error -> ErrorScreen(
            message = LocalContext.current.getString(designSystemR.string.error_message_generic),
            errorCode = state.code,
            imageVector = Icons.Default.Warning,
            onBackPressed = onBackClick
        )

        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.Success -> {
            if (state.data.leaveScreen) {
                onBackClick()
            } else {
                when (state.data.currentStep) {
                    OrderScreenState.ScreenStep.ADD_CLIENT -> {
                        AddClientStep(sendEvent = orderScreenViewModel::sendEvent, state)
                    }

                    OrderScreenState.ScreenStep.ADD_PRODUCTS -> {
                        AddProductsStep(sendEvent = orderScreenViewModel::sendEvent, state)
                    }

                    OrderScreenState.ScreenStep.FINISH_ORDER -> {
                        SaveOrderConfirmation(state)
                    }
                }
            }
        }
    }
}
