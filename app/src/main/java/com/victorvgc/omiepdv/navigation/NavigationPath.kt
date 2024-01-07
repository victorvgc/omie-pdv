package com.victorvgc.omiepdv.navigation

sealed class NavigationPath(val path: String) {
    data object DashboardPath : NavigationPath("dashboard")
    data object CreateOrderPath : NavigationPath("create-order")
    data object MorePath : NavigationPath("more")
}
