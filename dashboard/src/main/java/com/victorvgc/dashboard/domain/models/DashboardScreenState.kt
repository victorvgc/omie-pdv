package com.victorvgc.dashboard.domain.models

import com.victorvgc.design_system.ui.components.displays.IndicatorDirection
import java.math.BigDecimal

data class DashboardScreenState(
    val selectedFilter: DashboardFilter = DashboardFilter.ALL,
    val totalEarnings: BigDecimal = BigDecimal.ZERO,
    val totalClientsServed: Int = 0,
    val totalUnitsSold: Long = 0,
    val totalOrders: Int = 0,
    val totalEarningsDirection: IndicatorDirection = IndicatorDirection.NEUTRAL,
    val totalClientsDirection: IndicatorDirection = IndicatorDirection.NEUTRAL,
    val totalUnitsDirection: IndicatorDirection = IndicatorDirection.NEUTRAL,
    val totalOrdersDirection: IndicatorDirection = IndicatorDirection.NEUTRAL,
) {
    companion object {
        val Empty = DashboardScreenState()
    }
}
