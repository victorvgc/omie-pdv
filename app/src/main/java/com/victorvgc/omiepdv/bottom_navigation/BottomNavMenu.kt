package com.victorvgc.omiepdv.bottom_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.components.buttons.BaseFloatingActionButton
import com.victorvgc.design_system.ui.components.buttons.BottomNavButton
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.omiepdv.R
import com.victorvgc.design_system.R as designSystemR

enum class BottomNavMenuItem {
    DASHBOARD, CREATE_ORDER, MORE
}

@Composable
fun BottomNavMenu(
    modifier: Modifier = Modifier,
    onMenuItemClick: (BottomNavMenuItem) -> Unit
) {
    Column {
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            BottomNavButton(
                Modifier.weight(1f),
                icon = designSystemR.drawable.ic_dashboard,
                label = LocalContext.current.getString(R.string.dashboard)
            ) {
                onMenuItemClick(BottomNavMenuItem.DASHBOARD)
            }
            BaseFloatingActionButton(Modifier.weight(1f), icon = Icons.Default.Add) {
                onMenuItemClick(BottomNavMenuItem.CREATE_ORDER)
            }
            BottomNavButton(
                Modifier.weight(1f),
                icon = designSystemR.drawable.ic_more,
                label = LocalContext.current.getString(R.string.more)
            ) {
                onMenuItemClick(BottomNavMenuItem.MORE)
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewBottomNavMenu() {
    AppTheme {
        Column {
            BottomNavMenu {}
        }
    }
}
