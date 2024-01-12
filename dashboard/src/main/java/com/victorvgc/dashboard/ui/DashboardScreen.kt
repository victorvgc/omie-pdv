package com.victorvgc.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.dashboard.R
import com.victorvgc.dashboard.domain.models.DashboardFilter
import com.victorvgc.dashboard.domain.models.DashboardScreenState
import com.victorvgc.dashboard.ui.components.FilterMenu
import com.victorvgc.dashboard.ui.components.TotalEarningsDisplay
import com.victorvgc.dashboard.view_model.DashboardScreenEvent
import com.victorvgc.dashboard.view_model.DashboardViewModel
import com.victorvgc.design_system.ui.components.displays.CardDisplay
import com.victorvgc.design_system.ui.components.displays.IndicatorDirection
import com.victorvgc.design_system.ui.screens.ErrorScreen
import com.victorvgc.design_system.ui.screens.LoadingScreen
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.utils.VoidListener
import com.victorvgc.utils.extensions.toCurrency
import com.victorvgc.utils.extensions.toText
import com.victorvgc.utils.viewmodel.ScreenState
import java.math.BigDecimal
import com.victorvgc.design_system.R as designSystemR


@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    dashboardViewModel: DashboardViewModel,
    onNavigateToTotalOrders: VoidListener
) {
    val screenState by dashboardViewModel.screenState.collectAsState()

    when (val state = screenState) {
        is ScreenState.Error -> ErrorScreen(
            message = LocalContext.current.getString(designSystemR.string.error_message_generic),
            showBackButton = false
        ) {

        }

        is ScreenState.Loading -> LoadingScreen()
        is ScreenState.Success -> {
            DashboardScreen(
                modifier = modifier,
                state = state.data,
                onSelectFilter = {
                    dashboardViewModel.sendEvent(
                        DashboardScreenEvent.OnSelectFilter(it)
                    )
                },
                onClickTotalOrders = onNavigateToTotalOrders
            )
        }
    }
}

@Composable
private fun DashboardScreen(
    modifier: Modifier,
    state: DashboardScreenState,
    onSelectFilter: (DashboardFilter) -> Unit,
    onClickTotalOrders: VoidListener
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            FilterMenu(
                selected = state.selectedFilter,
                onSelectFilter = onSelectFilter
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            TotalEarningsDisplay(
                modifier = Modifier.fillMaxWidth(),
                data = state.totalEarnings,
                dataFormatter = { it.toCurrency() },
                direction = state.totalEarningsDirection
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    CardDisplay(
                        icon = designSystemR.drawable.ic_clients_count,
                        label = LocalContext.current.getString(R.string.total_clients_served),
                        data = state.totalClientsServed,
                        dataFormatter = { it.toText() },
                        direction = state.totalClientsDirection
                    )
                }

                item {
                    CardDisplay(
                        icon = designSystemR.drawable.ic_items_sold,
                        label = LocalContext.current.getString(R.string.total_units_sold),
                        data = state.totalUnitsSold,
                        dataFormatter = { it.toText() },
                        direction = state.totalUnitsDirection
                    )
                }

                item {
                    CardDisplay(
                        icon = designSystemR.drawable.ic_products_count,
                        label = LocalContext.current.getString(R.string.total_orders),
                        data = state.totalOrders,
                        dataFormatter = { it.toText() },
                        direction = state.totalOrdersDirection,
                        onClick = onClickTotalOrders
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDashboardScreen() {
    AppTheme {
        Scaffold {
            DashboardScreen(
                modifier = Modifier.padding(it),
                state = DashboardScreenState(
                    totalClientsServed = 10,
                    totalClientsDirection = IndicatorDirection.DOWN,
                    totalEarnings = BigDecimal(123456),
                    totalEarningsDirection = IndicatorDirection.UP,
                    totalOrders = 154,
                    totalOrdersDirection = IndicatorDirection.UP,
                    totalUnitsSold = 75,
                ),
                onSelectFilter = {},
                onClickTotalOrders = {}
            )
        }
    }
}
