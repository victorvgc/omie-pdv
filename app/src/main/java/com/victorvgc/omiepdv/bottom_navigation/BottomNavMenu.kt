package com.victorvgc.omiepdv.bottom_navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.omiepdv.R
import com.victorvgc.design_system.R as designSystemR

enum class BottomNavMenuItem {
    DASHBOARD, CREATE_ORDER, MORE
}

@Composable
fun BottomNavMenu(
    selectedItem: BottomNavMenuItem,
    onMenuItemClick: (BottomNavMenuItem) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedItem == BottomNavMenuItem.DASHBOARD,
            onClick = {
                onMenuItemClick(BottomNavMenuItem.DASHBOARD)
            },
            label = {
                Text(text = LocalContext.current.getString(R.string.dashboard))
            },
            icon = {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_dashboard),
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == BottomNavMenuItem.CREATE_ORDER,
            onClick = {
                onMenuItemClick(BottomNavMenuItem.CREATE_ORDER)
            },
            label = {
                Text(text = LocalContext.current.getString(R.string.create_order))
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = selectedItem == BottomNavMenuItem.MORE,
            onClick = {
                onMenuItemClick(BottomNavMenuItem.MORE)
            },
            label = {
                Text(text = LocalContext.current.getString(R.string.more))
            },
            icon = {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_more),
                    contentDescription = null
                )
            }
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewBottomNavMenu() {
    AppTheme {
        Column {
            BottomNavMenu(
                selectedItem = BottomNavMenuItem.DASHBOARD,
            ) {}
        }
    }
}
