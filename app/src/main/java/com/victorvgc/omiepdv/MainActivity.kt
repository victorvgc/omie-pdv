package com.victorvgc.omiepdv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.victorvgc.dashboard.ui.DashboardScreen
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.navigation.NavigationPath
import com.victorvgc.omiepdv.bottom_navigation.BottomNavMenuItem
import com.victorvgc.omiepdv.home.HomeScreen
import com.victorvgc.order_screen.ui.AddOrderScreen
import com.victorvgc.order_screen.viewmodel.OrderScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            AppTheme {
                NavHost(
                    navController = navController,
                    startDestination = NavigationPath.DashboardPath.path
                ) {
                    composable(NavigationPath.DashboardPath.path) {
                        HomeScreen(
                            onNavMenuClicked = {
                                when (it) {
                                    BottomNavMenuItem.DASHBOARD -> navController.navigate(
                                        NavigationPath.DashboardPath.link
                                    )

                                    BottomNavMenuItem.CREATE_ORDER -> navController.navigate(
                                        NavigationPath.OrderPath().link
                                    )

                                    BottomNavMenuItem.MORE -> navController.navigate(NavigationPath.MorePath.link)
                                }
                            },
                            content = { homeModifier ->
                                DashboardScreen(modifier = homeModifier)
                            }
                        )
                    }
                    composable(
                        NavigationPath.OrderPath().path,
                        arguments = listOf(navArgument(NavigationPath.OrderPath.ARG_USER_ID) {
                            defaultValue = NavigationPath.OrderPath.DEF_USER_ID
                        })
                    ) { backStackEntry ->
                        AddOrderScreen(
                            orderScreenViewModel = OrderScreenViewModel(
                                backStackEntry.arguments?.getLong(
                                    NavigationPath.OrderPath.ARG_USER_ID
                                ) ?: NavigationPath.OrderPath.DEF_USER_ID
                            ),
                            navController = navController
                        ) {

                        }
                    }
                    composable(NavigationPath.MorePath.path) {
                        ScreenPlaceholder(NavigationPath.MorePath.path)
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenPlaceholder(text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = text)
    }
}
