package com.victorvgc.dashboard.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.components.displays.IndicatorDirection
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.utils.extensions.toCurrency
import java.math.BigDecimal

@Composable
fun <T> TotalEarningsDisplay(
    modifier: Modifier = Modifier,
    data: T,
    dataFormatter: (T) -> String,
    direction: IndicatorDirection
) {
    Row(modifier = modifier) {
        val contentColor = when (direction) {
            IndicatorDirection.UP -> MaterialTheme.colorScheme.primary
            IndicatorDirection.DOWN -> MaterialTheme.colorScheme.error
            IndicatorDirection.NEUTRAL -> MaterialTheme.colorScheme.onSurface
        }

        @DrawableRes val indicatorIcon = when (direction) {
            IndicatorDirection.UP -> R.drawable.ic_up
            IndicatorDirection.DOWN -> R.drawable.ic_down
            IndicatorDirection.NEUTRAL -> R.drawable.ic_up
        }

        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = LocalContext.current.getString(R.string.currency),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraLight,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = dataFormatter(data),
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.ExtraLight,
                color = contentColor
            )
        )

        AnimatedVisibility(visible = direction != IndicatorDirection.NEUTRAL) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = indicatorIcon),
                contentDescription = null,
                tint = contentColor
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewTotalEarningsDisplay() {
    AppTheme {
        Column {
            TotalEarningsDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toCurrency() },
                direction = IndicatorDirection.UP
            )
            Spacer(modifier = Modifier.height(24.dp))
            TotalEarningsDisplay(
                data = BigDecimal(100),
                dataFormatter = { it.toCurrency() },
                direction = IndicatorDirection.DOWN
            )
            Spacer(modifier = Modifier.height(24.dp))
            TotalEarningsDisplay(
                data = BigDecimal(0),
                dataFormatter = { it.toCurrency() },
                direction = IndicatorDirection.NEUTRAL
            )
        }
    }
}
