package com.victorvgc.design_system.ui.components.displays

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.math.BigDecimal

enum class IndicatorDirection() {
    UP, DOWN
}

@Composable
fun <T> CardDisplay(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    label: String,
    data: T,
    dataFormatter: (T) -> String,
    showIndicator: Boolean = false,
    indicatorDirection: IndicatorDirection = IndicatorDirection.UP,
    onClick: (() -> Unit)? = null
) {
    val dataToString = dataFormatter(data)

    val dataIcon: Int = when (indicatorDirection) {
        IndicatorDirection.UP -> {
            R.drawable.ic_up
        }

        IndicatorDirection.DOWN -> {
            R.drawable.ic_down
        }
    }

    val dataColor = if (showIndicator) {
        when (indicatorDirection) {
            IndicatorDirection.UP -> {
                MaterialTheme.colorScheme.onPrimaryContainer
            }

            IndicatorDirection.DOWN -> {
                MaterialTheme.colorScheme.error
            }
        }
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    val componentModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Column(
        modifier = componentModifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
            .size(64.dp)
    ) {
        Image(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = icon),
            contentDescription = label,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dataToString,
                style = MaterialTheme.typography.bodyMedium.copy(color = dataColor)
            )
            AnimatedVisibility(visible = showIndicator) {
                Image(
                    painter = painterResource(id = dataIcon),
                    contentDescription = indicatorDirection.name,
                    colorFilter = ColorFilter.tint(dataColor)
                )
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewCardDisplay() {
    AppTheme {
        Column {
            CardDisplay(
                icon = R.drawable.ic_clients_count,
                label = "Clientes\nAtendidos",
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                showIndicator = true,
                indicatorDirection = IndicatorDirection.UP
            )
            CardDisplay(
                icon = R.drawable.ic_items_sold,
                label = "Unidades\nVendidas",
                data = BigDecimal(1000),
                dataFormatter = { it.toPlainString() },
                showIndicator = true,
                indicatorDirection = IndicatorDirection.DOWN
            )
            CardDisplay(
                icon = R.drawable.ic_products_count,
                label = "Pedidos\nFeitos",
                data = BigDecimal(100),
                dataFormatter = { it.toPlainString() },
                showIndicator = false
            )
        }
    }
}
