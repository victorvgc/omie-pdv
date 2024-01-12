package com.victorvgc.order_screen.viewmodel

import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.OrderProduct

sealed class OrderScreenEvent {
    data object SaveCurrentOrder : OrderScreenEvent()
    data class UpdateClientName(val value: String) : OrderScreenEvent()
    data class UpdateProductName(val value: String) : OrderScreenEvent()
    data class UpdateProductUnitPrice(val value: String) : OrderScreenEvent()
    data class UpdateProductQuantity(val value: String) : OrderScreenEvent()
    data object AddCurrentProduct : OrderScreenEvent()
    data object ShowAddCurrentProduct : OrderScreenEvent()
    data class RemoveProduct(val product: OrderProduct) : OrderScreenEvent()
    data object DiscardChanges : OrderScreenEvent()
    data object DismissDiscardChanges : OrderScreenEvent()
    data object ConfirmDiscardChanges : OrderScreenEvent()
    data object OnBackClicked : OrderScreenEvent()
    data object OnNextStepClicked : OrderScreenEvent()
    data class OnClientSelected(val client: Client) : OrderScreenEvent()
    data object OnEditClientClicked : OrderScreenEvent()
    data object OnConfirmProductsDialog : OrderScreenEvent()
    data object OnDismissProductsDialog : OrderScreenEvent()
    data object OnDeleteOrderClicked : OrderScreenEvent()
}
