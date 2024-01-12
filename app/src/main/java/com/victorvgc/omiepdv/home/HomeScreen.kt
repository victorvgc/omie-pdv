package com.victorvgc.omiepdv.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.omiepdv.R
import com.victorvgc.omiepdv.bottom_navigation.BottomNavMenu
import com.victorvgc.omiepdv.bottom_navigation.BottomNavMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    content: @Composable (Modifier) -> Unit,
    onNavMenuClicked: (BottomNavMenuItem) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = LocalContext.current.getString(R.string.dashboard))
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavMenu(
                selectedItem = BottomNavMenuItem.DASHBOARD,
                onMenuItemClick = onNavMenuClicked
            )
        }
    ) { padding ->
        content(Modifier.padding(padding))
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewHomeScreen() {
    AppTheme {
        HomeScreen(
            onNavMenuClicked = {},
            content = {},
        )
    }
}
