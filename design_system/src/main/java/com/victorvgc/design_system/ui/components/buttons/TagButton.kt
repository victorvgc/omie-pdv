package com.victorvgc.design_system.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun TagButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.outline
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(percent = 25),
                color = MaterialTheme.colorScheme.onPrimary
            )
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }

    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.secondary)
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewTagButton() {
    AppTheme {
        Column {
            TagButton(text = "Tag", isSelected = true) {}
            TagButton(text = "Tag") {}
        }
    }
}
