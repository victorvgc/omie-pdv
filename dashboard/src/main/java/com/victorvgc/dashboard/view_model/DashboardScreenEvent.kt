package com.victorvgc.dashboard.view_model

import com.victorvgc.dashboard.domain.models.DashboardFilter

sealed class DashboardScreenEvent {
    data class OnSelectFilter(val filter: DashboardFilter) : DashboardScreenEvent()
}
