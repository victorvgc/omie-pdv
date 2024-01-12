package com.victorvgc.order_screen.ui.add_edit_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.components.inputs.InputText
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.Client
import com.victorvgc.domain.core.Order
import com.victorvgc.order_screen.R
import com.victorvgc.order_screen.domain.models.OrderScreenState
import com.victorvgc.order_screen.ui.components.OrderAppBar
import com.victorvgc.order_screen.viewmodel.add_edit_order.OrderScreenEvent
import com.victorvgc.utils.StringListener
import com.victorvgc.utils.VoidListener
import com.victorvgc.utils.viewmodel.ScreenState
import com.victorvgc.design_system.R as designSystemR

@Composable
fun AddClientStep(
    sendEvent: (OrderScreenEvent) -> Unit,
    state: ScreenState.Success<OrderScreenState>
) {
    AddClientStepScreen(
        state = state.data,
        onBackClick = {
            sendEvent(OrderScreenEvent.OnBackClicked)
        },
        onClientNameUpdate = {
            sendEvent(OrderScreenEvent.UpdateClientName(it))
        },
        onClientSelected = {
            sendEvent(OrderScreenEvent.OnClientSelected(it))
        },
        onContinueClick = {
            sendEvent(OrderScreenEvent.OnNextStepClicked)
        },
        onDeleteOrder = {
            sendEvent(OrderScreenEvent.OnDeleteOrderClicked)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun AddClientStepScreen(
    state: OrderScreenState,
    onClientSelected: (Client) -> Unit,
    onClientNameUpdate: StringListener,
    onContinueClick: VoidListener,
    onBackClick: VoidListener,
    onDeleteOrder: VoidListener
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val enableContinue = state.enableContinue

    Scaffold(
        topBar = {
            OrderAppBar(
                scrollBehavior = scrollBehavior,
                isEdit = state.isEdit,
                onBackClick = onBackClick,
                onDeleteClick = onDeleteOrder
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = LocalContext.current.getString(designSystemR.string.action_continue)) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                },
                onClick = {
                    if (enableContinue) {
                        onContinueClick()
                    }
                },
                containerColor = if (enableContinue) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                },
                contentColor = if (enableContinue) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.outline
                }
            )
        }
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = LocalContext.current.getString(R.string.add_client))
            Spacer(modifier = Modifier.height(8.dp))
            InputText(
                modifier = Modifier.fillMaxWidth(),
                text = state.order.client.name,
                label = LocalContext.current.getString(R.string.hint_client_name),
                onTextChanged = onClientNameUpdate,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
            if (state.clientsList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(2f), thickness = 1.dp)
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(),
                        text = LocalContext.current.getString(R.string.or)
                    )
                    Divider(modifier = Modifier.weight(2f), thickness = 1.dp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = LocalContext.current.getString(R.string.select_existing_client))
                Spacer(modifier = Modifier.height(8.dp))

                var biggestNameSize = 1

                state.clientsList.forEach {
                    if (it.name.length > biggestNameSize)
                        biggestNameSize = it.name.length
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.clientsList.forEach { client ->
                        FilterChip(
                            onClick = { onClientSelected(client) },
                            label = { Text(text = client.name) },
                            selected = state.order.client == client,
                            trailingIcon = {
                                if (state.order.client == client) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
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
fun PreviewAddClient() {
    AppTheme {
        AddClientStepScreen(
            state = OrderScreenState(order = Order.Empty),
            onClientSelected = {},
            onClientNameUpdate = {},
            onContinueClick = {},
            onBackClick = {},
            onDeleteOrder = {}
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewAddClientWithClientList() {
    AppTheme {
        AddClientStepScreen(
            state =
            OrderScreenState(
                order = Order(id = 1, Client("Name 1")),
                isEdit = true,
                enableContinue = true,
                clientsList = listOf(
                    Client(name = "Name 1"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 12 as d"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 100 qewe qeweeqw"),
                    Client(name = "Name 2 as dasd"),
                    Client(name = "Name 12 as d"),
                )
            ),
            onClientSelected = {},
            onClientNameUpdate = {},
            onContinueClick = {},
            onBackClick = {},
            onDeleteOrder = {}
        )
    }
}

// endregion
