package com.victorvgc.order_screen.viewmodel

import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.order_screen.domain.OrderScreenState
import com.victorvgc.utils.extensions.execute
import com.victorvgc.utils.viewmodel.BaseViewModel
import java.math.BigDecimal
import kotlinx.coroutines.delay

class OrderScreenViewModel(
    orderId: Long
) : BaseViewModel<OrderScreenState, OrderScreenEvent>() {

    private var orderScreenState: OrderScreenState
    private var allClientsList: List<Client> = emptyList()
    private var allProductsList: List<Product> = emptyList()

    init {
        if (orderId != 0.toLong()) {
            orderScreenState = OrderScreenState(
                order = Order(id = orderId),
                isEdit = true,
                currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS
            )
            showLoading(preLoadedData = orderScreenState)
            // todo: fetch order from use case and display it
        } else {
            // todo: fetch latest order id and add 1
            orderScreenState = OrderScreenState(order = Order(id = 123))
        }

        // todo: fetch clients list
        // todo: fetch products list
        showSuccess(
            data = orderScreenState.copy(
                clientsList = allClientsList,
                productList = allProductsList
            )
        )
    }

    override fun onScreenEvent(event: OrderScreenEvent) {
        when (event) {
            OrderScreenEvent.AddCurrentProduct -> addCurrentProduct()
            OrderScreenEvent.SaveCurrentOrder -> saveOrder()
            is OrderScreenEvent.UpdateClientName -> updateClientName(event.value)
            is OrderScreenEvent.UpdateProductName -> updateProductName(event.value)
            is OrderScreenEvent.UpdateProductQuantity -> updateProductQuantity(event.value)
            is OrderScreenEvent.UpdateProductUnitPrice -> updateProductUnitPrice(event.value)
            is OrderScreenEvent.RemoveProduct -> removeProduct(event.product)
            OrderScreenEvent.DiscardChanges -> discardChanges()
            OrderScreenEvent.DismissDiscardChanges -> dismissDiscardChanges()
            OrderScreenEvent.ConfirmDiscardChanges -> confirmDiscardChanges()
            OrderScreenEvent.OnBackClicked -> handleBackClick()
            is OrderScreenEvent.OnClientSelected -> selectClient(event.client)
            OrderScreenEvent.OnNextStepClicked -> handleNextClicked()
            OrderScreenEvent.ShowAddCurrentProduct -> showProductForm()
            OrderScreenEvent.OnEditClientClicked -> handleEditClient()
        }
    }

    private fun addCurrentProduct() {
        val newOrder = orderScreenState.order.addProduct(
            orderScreenState.currentProduct.product,
            orderScreenState.currentProduct.quantity
        )

        orderScreenState = orderScreenState.copy(
            order = newOrder,
            showProductForm = false,
            enableAddProduct = false,
            currentProduct = OrderProduct.Empty
        )

        evaluateOrderToSave()

        showSuccess(data = orderScreenState)
    }

    private fun removeProduct(orderProduct: OrderProduct) {
        val newOrder = orderScreenState.order.removeProduct(orderProduct.product)
        orderScreenState = orderScreenState.copy(order = newOrder)

        evaluateOrderToSave()

        showSuccess(data = orderScreenState)
    }

    private fun saveOrder() {
        // todo: save into an use case
        handleNextClicked()
    }

    private fun updateClientName(name: String) {
        orderScreenState =
            orderScreenState.copy(order = orderScreenState.order.copy(client = Client(name = name)))

        filterClientName(name)
        evaluateClientInfo()

        showSuccess(data = orderScreenState)
    }

    private fun updateProductName(name: String) {
        orderScreenState =
            orderScreenState.copy(
                currentProduct = orderScreenState.currentProduct.copy(
                    product = orderScreenState.currentProduct.product.copy(
                        name = name
                    )
                )
            )

        evaluateOrderToSave()
        evaluateAddProduct()

        showSuccess(data = orderScreenState)
    }

    private fun updateProductUnitPrice(price: String) {
        var priceFromString = BigDecimal(price.ifEmpty { "0" })

        if (priceFromString != BigDecimal.ZERO) {
            priceFromString = priceFromString.divide(BigDecimal(100))
        }

        orderScreenState =
            orderScreenState.copy(
                currentProduct = orderScreenState.currentProduct.copy(
                    product = orderScreenState.currentProduct.product.copy(
                        unitPrice = priceFromString
                    )
                )
            )

        evaluateOrderToSave()
        evaluateAddProduct()

        showSuccess(data = orderScreenState)
    }

    private fun updateProductQuantity(quantity: String) {
        orderScreenState =
            orderScreenState.copy(
                currentProduct = orderScreenState.currentProduct.copy(
                    quantity = quantity.ifEmpty { "0" }.toLong()
                )
            )

        evaluateOrderToSave()
        evaluateAddProduct()

        showSuccess(data = orderScreenState)
    }

    private fun evaluateOrderToSave() {
        orderScreenState =
            if (orderScreenState.order.client.name.isNotEmpty() && orderScreenState.order.productList.isNotEmpty()) {
                orderScreenState.copy(enableContinue = true)
            } else {
                orderScreenState.copy(enableContinue = false)
            }
    }

    private fun evaluateClientInfo() {
        orderScreenState =
            if (orderScreenState.order.client.name.isNotEmpty()) {
                orderScreenState.copy(enableContinue = true)
            } else {
                orderScreenState.copy(enableContinue = false)
            }
    }

    private fun evaluateAddProduct() {
        orderScreenState =
            if (orderScreenState.currentProduct.product.name.isNotEmpty() &&
                orderScreenState.currentProduct.product.unitPrice > BigDecimal.ZERO &&
                orderScreenState.currentProduct.quantity > 0
            ) {
                orderScreenState.copy(enableAddProduct = true)
            } else {
                orderScreenState.copy(enableAddProduct = false)
            }
    }

    private fun discardChanges() {
        orderScreenState = orderScreenState.copy(showDiscardChangesDialog = true)

        showSuccess(orderScreenState)
    }

    private fun dismissDiscardChanges() {
        orderScreenState = orderScreenState.copy(showDiscardChangesDialog = false)

        showSuccess(orderScreenState)
    }

    private fun confirmDiscardChanges() {
        orderScreenState = orderScreenState.copy(leaveScreen = true)

        showSuccess(orderScreenState)
    }

    private fun handleBackClick() {
        orderScreenState = when (orderScreenState.currentStep) {
            OrderScreenState.ScreenStep.ADD_CLIENT -> {
                if (orderScreenState.isEdit) {
                    orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS)
                } else {
                    orderScreenState.copy(leaveScreen = true)
                }
            }

            OrderScreenState.ScreenStep.ADD_PRODUCTS -> {
                evaluateClientInfo()
                if (orderScreenState.isEdit) {
                    orderScreenState.copy(leaveScreen = true)
                } else {
                    orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_CLIENT)
                }
            }

            OrderScreenState.ScreenStep.FINISH_ORDER -> orderScreenState
        }

        showSuccess(orderScreenState)
    }

    private fun handleEditClient() {
        orderScreenState =
            orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_CLIENT)

        showSuccess(orderScreenState)
    }

    private fun selectClient(client: Client) {
        orderScreenState =
            orderScreenState.copy(order = orderScreenState.order.copy(client = client))

        showSuccess(orderScreenState)
    }

    private fun handleNextClicked() {
        orderScreenState = when (orderScreenState.currentStep) {
            OrderScreenState.ScreenStep.ADD_CLIENT -> {
                evaluateOrderToSave()
                orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS)
            }

            OrderScreenState.ScreenStep.ADD_PRODUCTS -> {
                // todo: evaluate if need to save any new product or client

                autoCloseAfterFinish()

                orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.FINISH_ORDER)
            }

            OrderScreenState.ScreenStep.FINISH_ORDER -> orderScreenState.copy(leaveScreen = true)
        }

        showSuccess(orderScreenState)
    }

    private fun filterClientName(clientTyped: String) {
        orderScreenState = if (clientTyped.isNotEmpty()) {
            val filteredClients = allClientsList.filter {
                it.name.contains(clientTyped, ignoreCase = true)
            }

            orderScreenState.copy(clientsList = filteredClients)
        } else {
            orderScreenState.copy(clientsList = allClientsList)
        }
    }

    private fun showProductForm() {
        orderScreenState =
            orderScreenState.copy(showProductForm = true)

        showSuccess(orderScreenState)
    }

    private fun autoCloseAfterFinish() {
        execute {
            delay(1200)
            orderScreenState = orderScreenState.copy(leaveScreen = true)
            showSuccess(orderScreenState)
        }
    }
}
