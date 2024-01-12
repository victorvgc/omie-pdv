package com.victorvgc.order_screen.ui.order_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.screens.ErrorScreen
import com.victorvgc.design_system.ui.screens.LoadingScreen
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.order_screen.R
import com.victorvgc.order_screen.domain.models.OrderListScreenState
import com.victorvgc.order_screen.viewmodel.order_list.OrderListViewModel
import com.victorvgc.utils.VoidListener
import com.victorvgc.utils.extensions.toCurrency
import com.victorvgc.utils.extensions.toText
import com.victorvgc.utils.viewmodel.ScreenState
import java.math.BigDecimal
import com.victorvgc.design_system.R as designSystemR

@Composable
fun OrderList(
    orderListViewModel: OrderListViewModel,
    onOrderClick: (Long) -> Unit,
    onBackPressed: VoidListener
) {
    val screenState by orderListViewModel.screenState.collectAsState()

    when (val state = screenState) {
        is ScreenState.Error -> ErrorScreen(
            message = LocalContext.current.getString(designSystemR.string.error_message_generic),
            onBackPressed = onBackPressed
        )

        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.Success -> {
            OrderListScreen(
                state = state.data,
                onOrderClick = {
                    onOrderClick(it.id)
                },
                onBackPressed = onBackPressed
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderListScreen(
    state: OrderListScreenState,
    onOrderClick: (Order) -> Unit,
    onBackPressed: VoidListener
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = LocalContext.current.getString(R.string.order_list_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = LocalContext.current.getString(designSystemR.string.action_back)
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.orderList) {
                ListItem(
                    modifier = Modifier.clickable { onOrderClick(it) },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        headlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        overlineColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        supportingColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        trailingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    overlineContent = {
                        Text(
                            text = LocalContext.current.getString(
                                R.string.order_replace,
                                it.id.toString()
                            )
                        )
                    },
                    headlineContent = {
                        Text(
                            text = LocalContext.current.getString(
                                R.string.client_name_replace,
                                it.client.name
                            )
                        )
                    },
                    supportingContent = {
                        Text(
                            text = LocalContext.current.getString(
                                R.string.products_count_replace,
                                it.totalUnits.toText()
                            )
                        )
                    },
                    trailingContent = {
                        Text(
                            text = LocalContext.current.getString(
                                designSystemR.string.currency_format,
                                it.totalPrice.toCurrency()
                            )
                        )
                    }
                )
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewOrderListScreen() {
    AppTheme {
        OrderListScreen(
            state = OrderListScreenState(
                orderList = listOf(
                    Order(
                        id = 1,
                        client = Client(name = "Client Name"),
                        productList = listOf(
                            OrderProduct(
                                Product(name = "Product Name", unitPrice = BigDecimal.ONE),
                                quantity = 123
                            ),
                        )
                    ),
                    Order(
                        id = 1,
                        client = Client(name = "Client Name"),
                        productList = listOf(
                            OrderProduct(
                                Product(name = "Product Name", unitPrice = BigDecimal.ONE),
                                quantity = 123
                            ),
                        )
                    ),
                    Order(
                        id = 1,
                        client = Client(name = "Client Name"),
                        productList = listOf(
                            OrderProduct(
                                Product(name = "Product Name", unitPrice = BigDecimal.ONE),
                                quantity = 123
                            ),
                        )
                    ),
                    Order(
                        id = 1,
                        client = Client(name = "Client Name"),
                        productList = listOf(
                            OrderProduct(
                                Product(name = "Product Name", unitPrice = BigDecimal.ONE),
                                quantity = 123
                            ),
                        )
                    ),
                )
            ),
            onOrderClick = {},
            onBackPressed = {}
        )
    }
}
