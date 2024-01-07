package com.victorvgc.design_system.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    negativeButton: String,
    positiveButton: String,
    onConfirmationListener: () -> Unit,
    onDismissListener: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onConfirmationListener) {
                Text(text = positiveButton)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissListener) {
                Text(text = negativeButton)
            }
        },
        onDismissRequest = {
            onDismissListener()
        })
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewBaseDialog() {
    AppTheme {
        Column {
            BaseDialog(
                title = "Alert Dialog",
                message = "[Message of the dialog that\nmight occupy a lot of\nspace, since it should explain\nthe problem properly]",
                negativeButton = "Dismiss",
                positiveButton = "Confirm",
                onConfirmationListener = {},
                onDismissListener = {}
            )
        }
    }
}
