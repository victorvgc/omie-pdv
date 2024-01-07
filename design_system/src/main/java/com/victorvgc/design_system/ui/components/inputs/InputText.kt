package com.victorvgc.design_system.ui.components.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    error: String? = null,
    onTextChanged: (String) -> Unit
) {

    val isError = error.isNullOrEmpty().not()

    val labelStyle = if (text.isEmpty()) {
        MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight(200))
    } else {
        MaterialTheme.typography.displaySmall
    }

    Column {
        TextField(
            modifier = modifier,
            value = text,
            onValueChange = onTextChanged,
            textStyle = MaterialTheme.typography.displayMedium,
            isError = isError,
            label = {
                Text(text = label, style = labelStyle)
            },

            )

        AnimatedVisibility(visible = isError) {
            Column {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = error!!,
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
                )
            }
        }
    }

}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewTextField() {
    AppTheme {
        Column {
            InputText(
                text = "",
                label = "Input field"
            ) {}

            InputText(
                text = "Input from user",
                label = "Input field"
            ) {}

            InputText(
                text = "",
                label = "Input field",
                error = "Error message that can tak a lot of space"
            ) {}
        }
    }
}
