package com.victorvgc.design_system.ui.components.displays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.victorvgc.design_system.ui.theme.AppTheme
import java.math.BigDecimal

@Composable
fun <T> DataDisplay(
    modifier: Modifier = Modifier,
    data: T,
    dataFormatter: (T) -> String,
    prefix: String? = null,
    postfix: String? = null,
    textSize: TextUnit = 18.sp,
    spacing: Dp = 4.dp
) {
    val dataToString = dataFormatter(data)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        AnimatedVisibility(visible = prefix.isNullOrEmpty().not()) {
            Row {
                Text(
                    text = prefix!!,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = textSize.times(0.75)
                    )
                )
                Spacer(modifier = Modifier.width(spacing))
            }
        }

        Text(
            text = dataToString,
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = textSize
            )
        )

        AnimatedVisibility(visible = postfix.isNullOrEmpty().not()) {
            Row {
                Spacer(modifier = Modifier.width(spacing))
                Text(
                    text = postfix!!,
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = textSize.times(0.75)
                    )
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
                postfix = "un."
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDataDisplaySmall() {
    val textSize = 14.sp

    AppTheme {
        Column {
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                textSize = textSize
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                prefix = "R$",
                textSize = textSize
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                postfix = "un.",
                textSize = textSize
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewDataDisplayHuge() {
    val textSize = 16.sp

    AppTheme {
        Column {
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                textSize = textSize
            )
            DataDisplay(
                data = 1234567890,
                dataFormatter = { it.toString() },
                prefix = "#",
                textSize = textSize
            )
            DataDisplay(
                data = BigDecimal(10000),
                dataFormatter = { it.toPlainString() },
                postfix = "un.",
                textSize = textSize
            )
        }
    }
}
