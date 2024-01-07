package com.victorvgc.design_system.ui.components.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewPrimaryButton() {
    AppTheme {
        Column {
            PrimaryButton(text = "Button") {}
            PrimaryButton(
                text = "Button",
                isEnabled = false
            ) {}
        }
    }
}
