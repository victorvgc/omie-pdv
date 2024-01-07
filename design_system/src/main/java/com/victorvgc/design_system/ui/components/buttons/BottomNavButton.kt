package com.victorvgc.design_system.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun BottomNavButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val componentColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    Column(
        modifier = modifier.clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = label,
            colorFilter = ColorFilter.tint(componentColor)
        )
        Text(text = label, style = MaterialTheme.typography.titleSmall.copy(color = componentColor))
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewBottomNavButton() {
    AppTheme {
        Column {
            BottomNavButton(
                icon = R.drawable.ic_dashboard,
                label = "Dashboard",
                isSelected = true
            ) {}
            BottomNavButton(
                icon = R.drawable.ic_dashboard,
                label = "Dashboard"
            ) {}
        }
    }
}
