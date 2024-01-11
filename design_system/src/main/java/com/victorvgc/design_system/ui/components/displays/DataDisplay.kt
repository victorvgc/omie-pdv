package com.victorvgc.design_system.ui.components.displays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun <T> DataDisplay(
    modifier: Modifier = Modifier,
    data: T,
    dataFormatter: (T) -> String,
    prefix: String? = null,
    suffix: String? = null,
    textStyle: TextStyle? = null,
    extrasStyle: TextStyle? = null,
    spacing: Dp = 4.dp,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.Bottom
) {
    val dataToString = dataFormatter(data)

    val contentStyle = textStyle ?: MaterialTheme.typography.bodyMedium
    val prefixSuffixStyle = extrasStyle ?: MaterialTheme.typography.labelSmall

    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {

        AnimatedVisibility(visible = prefix.isNullOrEmpty().not()) {
            Row {
                Text(
                    text = prefix!!,
                    style = prefixSuffixStyle
                )
                Spacer(modifier = Modifier.width(spacing))
            }
        }

        Text(
            text = dataToString,
            style = contentStyle
        )

        AnimatedVisibility(visible = suffix.isNullOrEmpty().not()) {
            Row {
                Spacer(modifier = Modifier.width(spacing))
                Text(
                    text = suffix!!,
                    style = prefixSuffixStyle
                )
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDataDisplay() {
    AppTheme {
        Column {
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                prefix = "R$"
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                suffix = "un."
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDataDisplaySmall() {
    AppTheme {
        Column {
            DataDisplay(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                prefix = "R$",
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                suffix = "un.",
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDataDisplayHuge() {
    AppTheme {
        Column {
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
            )
            DataDisplay(
                data = 1234567890,
                dataFormatter = { it.toString() },
                prefix = "#",
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                suffix = "un.",
            )
        }
    }
}
