package com.victorvgc.design_system.ui.components.inputs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
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
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    onTextChanged: (String) -> Unit
) {

    val isError = error.isNullOrEmpty().not()

    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = modifier,
            value = text,
            prefix = prefix,
            suffix = suffix,
            onValueChange = onTextChanged,
            singleLine = true,
            isError = isError,
            label = {
                Text(text = label)
            },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        AnimatedVisibility(visible = isError) {
            Column(
            ) {
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
        Column(modifier = Modifier.fillMaxWidth()) {
            InputText(
                text = "",
                label = "Input field"
            ) {}

            InputText(
                text = "Input from user",
                label = "Input field"
            ) {}

            InputText(
                modifier = Modifier.fillMaxWidth(),
                text = "",
                label = "Input field",
                error = "Error message that can tak a lot of space"
            ) {}
        }
    }
}
