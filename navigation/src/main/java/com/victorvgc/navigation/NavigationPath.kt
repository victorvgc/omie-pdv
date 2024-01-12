package com.victorvgc.navigation

sealed class NavigationPath(val path: String, val link: String = path) {
    data object DashboardPath : NavigationPath("dashboard")
    data class OrderPath(val orderId: Long = DEF_USER_ID) :
        NavigationPath("order/{$ARG_USER_ID}", link = "order/$orderId") {
        companion object {
            const val ARG_USER_ID = "userId"
            const val DEF_USER_ID = 0.toLong()
        }
    }

    data object MorePath : NavigationPath("more")

    data object OrderListPath : NavigationPath("order_list")
}
