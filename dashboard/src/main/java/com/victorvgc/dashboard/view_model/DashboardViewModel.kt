package com.victorvgc.dashboard.view_model

import com.victorvgc.core.domain.use_cases.GetAllOrdersUseCase
import com.victorvgc.core.domain.use_cases.GetOrdersFromToUseCase
import com.victorvgc.dashboard.domain.models.DashboardFilter
import com.victorvgc.dashboard.domain.models.DashboardScreenState
import com.victorvgc.design_system.ui.components.displays.IndicatorDirection
import com.victorvgc.domain.core.Order
import com.victorvgc.utils.extensions.execute
import com.victorvgc.utils.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.math.BigDecimal
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getOrdersFromToUseCase: GetOrdersFromToUseCase,
    private val getAllOrdersUseCase: GetAllOrdersUseCase
) : BaseViewModel<DashboardScreenState, DashboardScreenEvent>() {

    private enum class IntervalPeriod(val time: Int) {
        LAST_INTERVAL(-1),
        CURRENT_INTERVAL(0)
    }

    private data class TimeInterval(
        val startDate: Date = Date(),
        val endDate: Date = Date()
    )

    private var dashboardScreenState = DashboardScreenState()

    private var ordersList: List<Order> = emptyList()
    private var appliedFilter: DashboardFilter = DashboardFilter.ALL
    private var lastPeriodInterval: TimeInterval = TimeInterval()

    fun init() {
        if (dashboardScreenState == DashboardScreenState.Empty) {
            showLoading()
        }

        evaluateSelectedFilter(appliedFilter)
    }

    override fun onScreenEvent(event: DashboardScreenEvent) {
        when (event) {
            is DashboardScreenEvent.OnSelectFilter -> evaluateSelectedFilter(event.filter)
        }
    }

    private fun evaluateSelectedFilter(filter: DashboardFilter) {
        execute {
            dashboardScreenState = dashboardScreenState.copy(selectedFilter = filter)

            when (filter) {
                DashboardFilter.DAY -> getDayOrdersFilter()
                DashboardFilter.MONTH -> getMonthOrdersFilter()
                DashboardFilter.YEAR -> getYearOrdersFilter()
                DashboardFilter.ALL -> getAllOrdersFilter()
            }

            if (filter == DashboardFilter.ALL) {
                calculateCurrentEarnings(ordersList)
                calculateCurrentClientsServed(ordersList)
                calculateCurrentUnitsSold(ordersList)
                calculateCurrentOrdersDone(ordersList)

                showSuccess(dashboardScreenState)
            } else {
                val (startDate, endDate) = lastPeriodInterval

                val result = getOrdersFromToUseCase(startDate, endDate)

                if (result.isSuccess) {
                    val lastPeriodOrders = result.toSuccess()!!.data

                    calculateCurrentEarnings(lastPeriodOrders)
                    calculateCurrentClientsServed(lastPeriodOrders)
                    calculateCurrentUnitsSold(lastPeriodOrders)
                    calculateCurrentOrdersDone(lastPeriodOrders)

                    showSuccess(dashboardScreenState)
                } else {
                    showError()
                }
            }
        }
    }

    private suspend fun getAllOrdersFilter() {
        appliedFilter = DashboardFilter.ALL

        getAllOrdersUseCase()
            .onSuccess {
                ordersList = it
            }
            .onFailure {
                showError(it.code)
            }
    }

    private suspend fun getDayOrdersFilter() {
        appliedFilter = DashboardFilter.DAY
        lastPeriodInterval = getIntervalFrom(
            Calendar.DAY_OF_WEEK,
            IntervalPeriod.LAST_INTERVAL
        )

        val (startDate, endDate) = getIntervalFrom(Calendar.DAY_OF_WEEK)

        setOrderListToInterval(startDate, endDate)
    }

    private suspend fun getMonthOrdersFilter() {
        appliedFilter = DashboardFilter.MONTH
        lastPeriodInterval = getIntervalFrom(
            Calendar.MONTH,
            IntervalPeriod.LAST_INTERVAL
        )

        val (startDate, endDate) = getIntervalFrom(Calendar.MONTH)

        setOrderListToInterval(startDate, endDate)
    }

    private suspend fun getYearOrdersFilter() {
        appliedFilter = DashboardFilter.YEAR
        lastPeriodInterval = getIntervalFrom(
            Calendar.YEAR,
            IntervalPeriod.LAST_INTERVAL
        )

        val (startDate, endDate) = getIntervalFrom(Calendar.YEAR)

        setOrderListToInterval(startDate, endDate)
    }

    private suspend fun setOrderListToInterval(startDate: Date, endDate: Date) {
        getOrdersFromToUseCase(startDate, endDate)
            .onSuccess {
                ordersList = it
            }
            .onFailure {
                showError(it.code)
            }
    }

    private fun calculateCurrentEarnings(lastPeriodOrders: List<Order>) {
        val total: BigDecimal = calculateTotalEarningsFrom(ordersList)
        val lastTotal: BigDecimal = calculateTotalEarningsFrom(lastPeriodOrders)

        val direction = if (total > lastTotal) {
            IndicatorDirection.UP
        } else if (total < lastTotal) {
            IndicatorDirection.DOWN
        } else {
            IndicatorDirection.NEUTRAL
        }

        dashboardScreenState = dashboardScreenState.copy(
            totalEarnings = total,
            totalEarningsDirection = direction
        )
    }

    private fun calculateTotalEarningsFrom(list: List<Order>): BigDecimal {
        return list.map { it.totalPrice }.takeIf { it.isNotEmpty() }?.reduce { acc, next ->
            acc + next
        } ?: BigDecimal.ZERO
    }

    private fun calculateCurrentClientsServed(lastPeriodOrders: List<Order>) {
        val total = calculateClientsServedFrom(ordersList)
        val lastTotal = calculateClientsServedFrom(lastPeriodOrders)

        val direction = if (total > lastTotal) {
            IndicatorDirection.UP
        } else if (total < lastTotal) {
            IndicatorDirection.DOWN
        } else {
            IndicatorDirection.NEUTRAL
        }

        dashboardScreenState = dashboardScreenState.copy(
            totalClientsServed = total,
            totalClientsDirection = direction
        )
    }

    private fun calculateClientsServedFrom(list: List<Order>): Int =
        list.map { it.client }.distinct().size

    private fun calculateCurrentUnitsSold(lastPeriodOrders: List<Order>) {
        val total = calculateUnitsSoldFrom(ordersList)
        val lastTotal = calculateUnitsSoldFrom(lastPeriodOrders)

        val direction = if (total > lastTotal) {
            IndicatorDirection.UP
        } else if (total < lastTotal) {
            IndicatorDirection.DOWN
        } else {
            IndicatorDirection.NEUTRAL
        }

        dashboardScreenState = dashboardScreenState.copy(
            totalUnitsSold = total,
            totalUnitsDirection = direction
        )
    }

    private fun calculateUnitsSoldFrom(list: List<Order>): Long =
        list.map { it.totalUnits }.takeIf { it.isNotEmpty() }?.reduce { acc, next -> acc + next }
            ?: 0

    private fun calculateCurrentOrdersDone(lastPeriodOrders: List<Order>) {
        val total = ordersList.size
        val lastTotal = lastPeriodOrders.size

        val direction = if (total > lastTotal) {
            IndicatorDirection.UP
        } else if (total < lastTotal) {
            IndicatorDirection.DOWN
        } else {
            IndicatorDirection.NEUTRAL
        }

        dashboardScreenState = dashboardScreenState.copy(
            totalOrders = total,
            totalOrdersDirection = direction
        )
    }

    private fun getIntervalFrom(
        intervalSpan: Int,
        intervalPeriod: IntervalPeriod = IntervalPeriod.CURRENT_INTERVAL
    ): TimeInterval {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()

        startDate.add(
            intervalSpan,
            intervalPeriod.time - 1
        )

        endDate.add(
            intervalSpan,
            intervalPeriod.time
        )

        return TimeInterval(startDate.time, endDate.time)
    }
}
