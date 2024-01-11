package com.victorvgc.order_screen.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.utils.extensions.toText

@Composable
fun ProductUnitSelector(
    quantity: Long,
    onDelete: () -> Unit
) {
    val shape = RoundedCornerShape(4.dp)
    val contentColor = MaterialTheme.colorScheme.onSurface

    val minWidth = 80
    val extraDigitWidth = 4

    val componentWidth = if (quantity >= 10000) {
        var extra = 1
        var extraQuantity = quantity

        while (extraQuantity >= 10000) {
            extra++
            extraQuantity /= 10
        }

        minWidth + (extraDigitWidth * extra)
    } else {
        minWidth
    }

    Card(
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(shape = shape, color = MaterialTheme.colorScheme.surface)
                .border(
                    BorderStroke(width = 1.dp, MaterialTheme.colorScheme.outlineVariant),
                    shape = shape
                )
                .width(componentWidth.dp)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = quantity.toText(),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    color = contentColor
                )
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .clickable { onDelete() },
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

// region Preview

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewProductUnitSelector() {
    AppTheme {
        Column {
            ProductUnitSelector(1) {}
            ProductUnitSelector(10) {}
            ProductUnitSelector(100) {}
            ProductUnitSelector(1000) {}
            ProductUnitSelector(1000000) {}
        }
    }
}

// endregion
