package com.victorvgc.design_system.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun BaseImageButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    color: Color,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.padding(16.dp)) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(color)
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewBaseImageButton() {
    AppTheme {
        Column {
            BaseImageButton(
                icon = R.drawable.ic_clients_count,
                color = MaterialTheme.colorScheme.primary
            ) {}
            BaseImageButton(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer),
                icon = R.drawable.ic_products_count,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ) {}
            BaseImageButton(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.error),
                icon = R.drawable.ic_dashboard,
                color = MaterialTheme.colorScheme.onError
            ) {}
        }
    }
}
