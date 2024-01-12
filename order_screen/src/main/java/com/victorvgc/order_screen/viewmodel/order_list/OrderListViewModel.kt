package com.victorvgc.order_screen.viewmodel.order_list

import com.victorvgc.core.domain.use_cases.GetAllOrdersUseCase
import com.victorvgc.order_screen.domain.models.OrderListScreenState
import com.victorvgc.utils.extensions.execute
import com.victorvgc.utils.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : BaseViewModel<OrderListScreenState, OrderListScreenEvent>() {

    private var orderListScreenState = OrderListScreenState()

    fun init() {
        showLoading()
        execute {
            getAllOrdersUseCase()
                .onSuccess {
                    orderListScreenState = orderListScreenState.copy(orderList = it)

                    showSuccess(orderListScreenState)
                }
                .onFailure {
                    showError(it.code)
                }
        }
    }

    override fun onScreenEvent(event: OrderListScreenEvent) {}
}
