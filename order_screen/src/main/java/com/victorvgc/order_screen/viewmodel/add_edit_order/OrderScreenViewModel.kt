package com.victorvgc.order_screen.viewmodel.add_edit_order

import com.victorvgc.core.domain.use_cases.DeleteOrderUseCase
import com.victorvgc.core.domain.use_cases.GetAllClientsUseCase
import com.victorvgc.core.domain.use_cases.GetAllProductsUseCase
import com.victorvgc.core.domain.use_cases.GetHighestOrderByIdUseCase
import com.victorvgc.core.domain.use_cases.GetOrderUseCase
import com.victorvgc.core.domain.use_cases.SaveClientUseCase
import com.victorvgc.core.domain.use_cases.SaveOrderUseCase
import com.victorvgc.core.domain.use_cases.SaveProductUseCase
import com.victorvgc.core.domain.use_cases.UpdateOrderUseCase
import com.victorvgc.core.domain.use_cases.UpdateProductUseCase
import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.order_screen.domain.models.OrderScreenState
import com.victorvgc.utils.extensions.async
import com.victorvgc.utils.extensions.execute
import com.victorvgc.utils.failures.FailureCodes
import com.victorvgc.utils.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import javax.inject.Inject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay

@HiltViewModel
class OrderScreenViewModel @Inject constructor(
    private val getHighestOrderByIdUseCase: GetHighestOrderByIdUseCase,
    private val saveOrderUseCase: SaveOrderUseCase,
    private val getAllClientsUseCase: GetAllClientsUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val saveClientUseCase: SaveClientUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
) :
    BaseViewModel<OrderScreenState, OrderScreenEvent>() {

    private var orderScreenState: OrderScreenState = OrderScreenState(order = Order.Empty)

    private var allClientsList: List<Client> = emptyList()
    private var allProductsList: List<Product> = emptyList()

    private var productsToAdd: List<Product> = emptyList()
    private var productsToUpdate: List<Product> = emptyList()

    var orderId: Long = 0
        set(value) {
            init(value)

            field = value
        }

    private fun init(orderId: Long) {
        execute {
            if (orderId != 0.toLong()) {
                orderScreenState = OrderScreenState(
                    order = Order(id = orderId),
                    isEdit = true,
                    currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS
                )
                showLoading(preLoadedData = orderScreenState)

                val orderResult = getOrderUseCase(orderId)

                if (orderResult.isSuccess) {
                    orderResult.onSuccess {
                        orderScreenState = orderScreenState.copy(order = it)
                    }
                } else {
                    showError(code = FailureCodes.FAILURE_CODE_READ.code)

                    return@execute
                }
            } else {
                val highestOrderIdResult = getHighestOrderByIdUseCase()

                if (highestOrderIdResult.isSuccess) {
                    highestOrderIdResult.onSuccess {
                        val latestOrderId = it.id

                        orderScreenState = OrderScreenState(order = Order(id = latestOrderId + 1))
                    }
                } else {
                    showError(code = FailureCodes.FAILURE_CODE_READ.code)

                    return@execute
                }
            }

            val allClientsResult = getAllClientsUseCase()

            val allProductsResult = getAllProductsUseCase()

            if (allClientsResult.isSuccess && allProductsResult.isSuccess) {
                allClientsResult.onSuccess {
                    allClientsList = it
                }

                allProductsResult.onSuccess {
                    allProductsList = it
                }

                orderScreenState = orderScreenState.copy(
                    clientsList = allClientsList,
                    productList = allProductsList
                )

                showSuccess(orderScreenState)
            } else {
                showError(code = FailureCodes.FAILURE_CODE_READ.code)
            }
        }
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
            OrderScreenEvent.OnConfirmProductsDialog -> saveAndUpdateProducts()
            OrderScreenEvent.OnDismissProductsDialog -> dismissProductDialog()
            OrderScreenEvent.OnDeleteOrderClicked -> deleteCurrentOrderAndFinish()
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
        execute {
            val result = if (orderScreenState.isEdit) {
                updateOrderUseCase(orderScreenState.order)
            } else {
                saveOrderUseCase(orderScreenState.order)
            }

            result.onSuccess {
                handleNextClicked()
            }.onFailure {
                showError(it.code)
            }
        }

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
        evaluateProductAlreadyExists()

        showSuccess(data = orderScreenState)
    }

    private fun evaluateProductAlreadyExists() {
        val productExists =
            allProductsList.find {
                it.name.equals(
                    orderScreenState.currentProduct.product.name,
                    ignoreCase = true
                )
            }

        if (productExists != null) {
            orderScreenState =
                orderScreenState.copy(currentProduct = orderScreenState.currentProduct.copy(product = productExists))
        }
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
        resetStateAndLeave()

        showSuccess(orderScreenState)
    }

    private fun handleBackClick() {
        when (orderScreenState.currentStep) {
            OrderScreenState.ScreenStep.ADD_CLIENT -> {
                if (orderScreenState.isEdit) {
                    orderScreenState =
                        orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS)

                    showSuccess(orderScreenState)
                } else {
                    resetStateAndLeave()
                }
            }

            OrderScreenState.ScreenStep.ADD_PRODUCTS -> {
                evaluateClientInfo()
                if (orderScreenState.isEdit) {
                    resetStateAndLeave()
                } else {
                    orderScreenState =
                        orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_CLIENT)

                    showSuccess(orderScreenState)
                }
            }

            OrderScreenState.ScreenStep.FINISH_ORDER -> {}
        }
    }

    private fun handleEditClient() {
        orderScreenState =
            orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_CLIENT)

        showSuccess(orderScreenState)
    }

    private fun selectClient(client: Client) {
        orderScreenState =
            orderScreenState.copy(order = orderScreenState.order.copy(client = client))

        evaluateClientInfo()

        showSuccess(orderScreenState)
    }

    private fun handleNextClicked() {
        orderScreenState = when (orderScreenState.currentStep) {
            OrderScreenState.ScreenStep.ADD_CLIENT -> {
                evaluateOrderToSave()
                orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.ADD_PRODUCTS)
            }

            OrderScreenState.ScreenStep.ADD_PRODUCTS -> {
                showLoading()
                evaluateNewClient()
                if (haveToUpdateProducts().not()) {
                    autoCloseAfterFinish()

                    orderScreenState.copy(currentStep = OrderScreenState.ScreenStep.FINISH_ORDER)
                } else {
                    orderScreenState
                }
            }

            OrderScreenState.ScreenStep.FINISH_ORDER -> resetStateAndLeave()
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

            resetStateAndLeave()
        }
    }

    private fun resetStateAndLeave(): OrderScreenState {
        orderScreenState = OrderScreenState(Order.Empty, leaveScreen = true)

        showSuccess(orderScreenState)

        orderScreenState = orderScreenState.copy(leaveScreen = false)

        return orderScreenState
    }

    private fun evaluateNewClient() {
        val client = orderScreenState.order.client

        if (allClientsList.contains(client).not()) {
            execute {
                saveClientUseCase(client)
            }
        }
    }

    private fun haveToUpdateProducts(): Boolean {
        val orderProducts = orderScreenState.order.productList

        var showDialog = false

        val productsToAdd = mutableListOf<Product>()
        val productsToUpdate = mutableListOf<Product>()

        orderProducts.forEach { orderProduct ->
            val product = allProductsList.firstOrNull { it.name == orderProduct.product.name }

            if (product == null || product.unitPrice != orderProduct.product.unitPrice) {
                showDialog = true

                if (product == null) {
                    productsToAdd.add(orderProduct.product)
                } else {
                    productsToUpdate.add(orderProduct.product)
                }
            }
        }

        if (showDialog) {
            orderScreenState = orderScreenState.copy(
                showUpdateProductsDialog = true,
                productsToCreate = productsToAdd.size,
                productsToUpdate = productsToUpdate.size
            )

            this.productsToAdd = productsToAdd
            this.productsToUpdate = productsToUpdate

            return true
        }

        return false
    }

    private fun saveAndUpdateProducts() {
        execute {
            val waitAllJobsList = mutableListOf<Deferred<Any>>()

            productsToAdd.forEach {
                waitAllJobsList.add(async { saveProductUseCase(it) })
            }

            productsToUpdate.forEach {
                waitAllJobsList.add(async { updateProductUseCase(it) })
            }

            orderScreenState = orderScreenState.copy(
                showUpdateProductsDialog = false,
                currentStep = OrderScreenState.ScreenStep.FINISH_ORDER
            )

            waitAllJobsList.forEach {
                it.await()
            }

            autoCloseAfterFinish()

            showSuccess(orderScreenState)
        }
    }

    private fun dismissProductDialog() {
        orderScreenState = orderScreenState.copy(
            showUpdateProductsDialog = false,
            currentStep = OrderScreenState.ScreenStep.FINISH_ORDER
        )

        autoCloseAfterFinish()

        showSuccess(orderScreenState)
    }

    private fun deleteCurrentOrderAndFinish() {
        execute {
            showLoading()
            deleteOrderUseCase(orderScreenState.order)
                .onSuccess {
                    resetStateAndLeave()
                }
                .onFailure {
                    showError(it.code)
                }
        }
    }
}
