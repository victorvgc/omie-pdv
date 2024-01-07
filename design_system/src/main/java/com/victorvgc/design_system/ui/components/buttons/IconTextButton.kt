package com.victorvgc.design_system.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val componentColor = if (isEnabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    val componentModifier = if (isEnabled) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Row(
        modifier = componentModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = text,
            colorFilter = ColorFilter.tint(componentColor)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(text = text, style = MaterialTheme.typography.labelMedium.copy(color = componentColor))
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewIconTextButton() {
    AppTheme {
        Column {
            IconTextButton(icon = R.drawable.ic_products_count, text = "Icon text button") {}
            IconTextButton(
                icon = R.drawable.ic_products_count,
                text = "Icon text button",
                isEnabled = false
            ) {}
        }
    }
}
