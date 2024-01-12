package com.victorvgc.design_system.ui.components.displays

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                MaterialTheme.colorScheme.primary
            }

            IndicatorDirection.DOWN -> {
                MaterialTheme.colorScheme.error
            }
        }
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val componentModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Card(
        modifier = componentModifier.size(90.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = componentModifier
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dataToString,
                    style = MaterialTheme.typography.bodyLarge.copy(color = dataColor)
                )
                AnimatedVisibility(visible = showIndicator) {
                    Icon(
                        painter = painterResource(id = dataIcon),
                        contentDescription = indicatorDirection.name,
                        tint = dataColor
                    )
                }
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
