package com.victorvgc.order_screen.ui.add_edit_order

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.components.displays.DataDisplay
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.order_screen.R
import com.victorvgc.order_screen.domain.models.OrderScreenState
import com.victorvgc.order_screen.ui.components.AddProductBottomSheet
import com.victorvgc.order_screen.ui.components.AddProductListItem
import com.victorvgc.order_screen.ui.components.AddUpdateProductsDialog
import com.victorvgc.order_screen.ui.components.OrderAppBar
import com.victorvgc.order_screen.ui.components.ProductListItem
import com.victorvgc.order_screen.viewmodel.add_edit_order.OrderScreenEvent
import com.victorvgc.utils.StringListener
import com.victorvgc.utils.VoidListener
import com.victorvgc.utils.viewmodel.ScreenState
import java.math.BigDecimal
import kotlinx.coroutines.launch
import com.victorvgc.design_system.R as designSystemR

@Composable
fun AddProductsStep(
    sendEvent: (OrderScreenEvent) -> Unit,
    state: ScreenState.Success<OrderScreenState>
) {

    AddProductsScreen(
        state = state.data,
        onAddProduct = { sendEvent(OrderScreenEvent.AddCurrentProduct) },
        onShowProductForm = { sendEvent(OrderScreenEvent.ShowAddCurrentProduct) },
        onProductQuantityChange = {
            sendEvent(
                OrderScreenEvent.UpdateProductQuantity(
                    it
                )
            )
        },
        onProductNameChange = {
            sendEvent(
                OrderScreenEvent.UpdateProductName(
                    it
                )
            )
        },
        onProductPriceChange = {
            sendEvent(
                OrderScreenEvent.UpdateProductUnitPrice(
                    it
                )
            )
        },
        onRemoveProduct = {
            sendEvent(
                OrderScreenEvent.RemoveProduct(it)
            )
        },
        onBackClick = {
            sendEvent(
                OrderScreenEvent.OnBackClicked
            )
        },
        onSaveClick = {
            sendEvent(
                OrderScreenEvent.SaveCurrentOrder
            )
        },
        onEditClient = {
            sendEvent(
                OrderScreenEvent.OnEditClientClicked
            )
        },
        onConfirmProductDialog = {
            sendEvent(
                OrderScreenEvent.OnConfirmProductsDialog
            )
        },
        onDismissProductDialog = {
            sendEvent(
                OrderScreenEvent.OnDismissProductsDialog
            )
        },
        onDeleteOrder = {
            sendEvent(
                OrderScreenEvent.OnDeleteOrderClicked
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProductsScreen(
    state: OrderScreenState,
    onAddProduct: VoidListener,
    onShowProductForm: VoidListener,
    onProductNameChange: StringListener,
    onProductPriceChange: StringListener,
    onProductQuantityChange: StringListener,
    onRemoveProduct: (OrderProduct) -> Unit,
    onBackClick: VoidListener,
    onSaveClick: VoidListener,
    onEditClient: VoidListener,
    onDismissProductDialog: VoidListener,
    onConfirmProductDialog: VoidListener,
    onDeleteOrder: VoidListener
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state.showProductForm) {
        if (state.showProductForm) {
            scope.launch {
                scaffoldState.bottomSheetState.expand()
            }
        } else {
            scope.launch {
                scaffoldState.bottomSheetState.partialExpand()
            }
        }
    }

    BottomSheetScaffold(
        topBar = {
            OrderAppBar(
                scrollBehavior = scrollBehavior,
                isEdit = state.isEdit,
                onDeleteClick = onDeleteOrder,
                onBackClick = onBackClick
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 100.dp,
        sheetDragHandle = {},
        sheetShape = RectangleShape,
        sheetShadowElevation = 8.dp,
        sheetContent = {
            AddProductBottomSheet(
                order = state.order,
                enableSave = state.enableContinue,
                showSave = scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded,
                currentProduct = state.currentProduct,
                enableAddProductButton = state.enableAddProduct,
                onProductNameChange = onProductNameChange,
                onProductPriceChange = onProductPriceChange,
                onProductQuantityChange = onProductQuantityChange,
                onSaveOrder = onSaveClick,
                onAddProduct = {
                    onAddProduct()
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )
        }) {
        Scaffold { padding ->
            if (state.showUpdateProductsDialog) {
                AddUpdateProductsDialog(
                    newProductsCount = state.productsToCreate,
                    updateProductsCount = state.productsToUpdate,
                    onDismiss = onDismissProductDialog,
                    onConfirmListener = onConfirmProductDialog
                )
            }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                DataDisplay(
                    data = state.order.id,
                    dataFormatter = {
                        it.toString()
                    },
                    prefix = LocalContext.current.getString(R.string.order_prefix),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    extrasStyle = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.order_client),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.order.client.name,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AnimatedVisibility(visible = state.isEdit) {
                        IconButton(onClick = onEditClient) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = LocalContext.current.getString(designSystemR.string.action_edit),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.products_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AnimatedVisibility(visible = scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded) {
                            AddProductListItem {
                                onShowProductForm()
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                }
                            }
                        }
                    }

                    items(state.order.productList.reversed()) { orderProduct ->
                        ProductListItem(orderProduct = orderProduct, onRemove = onRemoveProduct)
                    }
                }
            }
        }
    }
}

// region Preview

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewAddProductsStep() {
    val order = Order(id = 123456, Client("Client 1"))
        .addProduct(
            Product(
                name = "Product number 1 that have a really large name",
                unitPrice = BigDecimal(123)
            ), quantity = 45
        )
        .addProduct(Product(name = "Product number 2", unitPrice = BigDecimal(9)), quantity = 120)
        .addProduct(
            Product(name = "Product number 6", unitPrice = BigDecimal(100)),
            quantity = 1200
        )
        .addProduct(
            Product(name = "Product number 3", unitPrice = BigDecimal(12.23)),
            quantity = 145
        )
        .addProduct(
            Product(name = "Product number 5", unitPrice = BigDecimal(10002)),
            quantity = 3
        )
        .addProduct(
            Product(name = "Product number 5", unitPrice = BigDecimal(1.02)),
            quantity = 30000
        )

    AppTheme {
        AddProductsScreen(
            state =
            OrderScreenState(
                order = order,
                showProductForm = false,
                isEdit = true,
            ),
            onAddProduct = {},
            onShowProductForm = {},
            onProductNameChange = {},
            onProductPriceChange = {},
            onProductQuantityChange = {},
            onRemoveProduct = {},
            onBackClick = {},
            onSaveClick = {},
            onEditClient = {},
            onDismissProductDialog = {},
            onConfirmProductDialog = {},
            onDeleteOrder = {}
        )
    }
}

// endregion
