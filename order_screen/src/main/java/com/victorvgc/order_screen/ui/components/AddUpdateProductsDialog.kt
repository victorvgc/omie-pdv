package com.victorvgc.order_screen.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.order_screen.R
import com.victorvgc.utils.VoidListener
import com.victorvgc.design_system.R as designSystemR

@Composable
fun AddUpdateProductsDialog(
    newProductsCount: Int,
    updateProductsCount: Int,
    onDismiss: VoidListener,
    onConfirmListener: VoidListener
) {
    val text = buildAnnotatedString {
        append(LocalContext.current.getString(R.string.we_noticed))
        append(" ")
        if (updateProductsCount > 0) {
            if (updateProductsCount > 1) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(
                        LocalContext.current.getString(
                            R.string.plural_product,
                            updateProductsCount.toString()
                        )
                    )
                }
                append(" ")
                append(LocalContext.current.getString(R.string.plural_were_modified))
                append(" ")
            } else {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(
                        LocalContext.current.getString(
                            R.string.singular_product,
                            updateProductsCount.toString()
                        )
                    )
                }
                append(" ")
                append(LocalContext.current.getString(R.string.singular_were_modified))
                append(" ")
            }
        }

        if (newProductsCount > 0 && updateProductsCount > 0) {
            append(LocalContext.current.getString(R.string.and))
            append(" ")
        }

        if (newProductsCount > 0) {
            if (newProductsCount > 1) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(
                        LocalContext.current.getString(
                            R.string.plural_product,
                            newProductsCount.toString()
                        )
                    )
                }
                append(" ")
                append(LocalContext.current.getString(R.string.plural_were_created))
                append(" ")
            } else {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(
                        LocalContext.current.getString(
                            R.string.singular_product,
                            newProductsCount.toString()
                        )
                    )
                }
                append(" ")
                append(LocalContext.current.getString(R.string.singular_were_created))
                append(" ")
            }
        }

        append("\n")
        append("\n")
        append(LocalContext.current.getString(R.string.save_product_changes))
    }

    AlertDialog(
        icon = {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
        },
        title = {
            Text(text = "Antes de continuar...")
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            TextButton(onClick = onConfirmListener) {
                Text(text = LocalContext.current.getString(designSystemR.string.action_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = LocalContext.current.getString(designSystemR.string.action_no))
            }
        },
        onDismissRequest = onDismiss
    )
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewAddUpdateProductsDialog() {
    AppTheme {
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                AddUpdateProductsDialog(
                    newProductsCount = 2,
                    updateProductsCount = 1,
                    onDismiss = {},
                    onConfirmListener = {}
                )
            }
        }
    }
}
