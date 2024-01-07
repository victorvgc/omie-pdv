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
import com.victorvgc.dashboard.ui.DashboardScreen
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.omiepdv.bottom_navigation.BottomNavMenuItem
import com.victorvgc.omiepdv.home.HomeScreen
import com.victorvgc.omiepdv.navigation.NavigationPath

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            AppTheme {
                HomeScreen(
                    onNavMenuClicked = {
                        when (it) {
                            BottomNavMenuItem.DASHBOARD -> navController.navigate(NavigationPath.DashboardPath.path)
                            BottomNavMenuItem.CREATE_ORDER -> navController.navigate(NavigationPath.CreateOrderPath.path)
                            BottomNavMenuItem.MORE -> navController.navigate(NavigationPath.MorePath.path)
                        }
                    },
                    content = { homeModifier ->
                        NavHost(
                            navController = navController,
                            startDestination = NavigationPath.DashboardPath.path
                        ) {
                            composable(NavigationPath.DashboardPath.path) {
                                DashboardScreen(modifier = homeModifier)
                            }
                            composable(NavigationPath.MorePath.path) {
                                ScreenPlaceholder(NavigationPath.MorePath.path)
                            }
                            composable(NavigationPath.CreateOrderPath.path) {
                                ScreenPlaceholder(NavigationPath.CreateOrderPath.path)
                            }
                        }
                    }
                )
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
