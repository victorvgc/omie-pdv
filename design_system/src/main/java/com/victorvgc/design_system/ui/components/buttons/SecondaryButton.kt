package com.victorvgc.design_system.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val borderColor = if (isEnabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }

    OutlinedButton(
        modifier = modifier,
        enabled = isEnabled,
        border = BorderStroke(1.dp, borderColor),
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewSecondaryButton() {
    AppTheme {
        Column {
            SecondaryButton(text = "Button") {}
            SecondaryButton(
                text = "Button",
                isEnabled = false
            ) {}
        }
    }
}
