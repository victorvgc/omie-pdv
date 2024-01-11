package com.victorvgc.order_screen.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.components.displays.DataDisplay
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.Order
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.order_screen.R
import com.victorvgc.utils.StringListener
import com.victorvgc.utils.VoidListener
import com.victorvgc.utils.extensions.removeStartingTrailingZeros
import com.victorvgc.utils.extensions.toCurrency
import com.victorvgc.utils.extensions.toText
import com.victorvgc.utils.text_masks.NumberAppearance
import com.victorvgc.utils.text_masks.TextMaskTransformation
import java.math.BigDecimal
import com.victorvgc.design_system.R as designSystemR

@Composable
fun AddProductBottomSheet(
    order: Order,
    showSave: Boolean,
    enableSave: Boolean,
    currentProduct: OrderProduct,
    enableAddProductButton: Boolean,
    onProductNameChange: StringListener,
    onProductPriceChange: StringListener,
    onProductQuantityChange: StringListener,
    onAddProduct: VoidListener,
    onSaveOrder: VoidListener
) {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        BottomSheetHeader(
            order = order,
            showSave = showSave,
            enableSave = enableSave,
            onSaveOrder = onSaveOrder
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentProduct.product.name,
            label = {
                Text(text = LocalContext.current.getString(R.string.hint_product_name))
            },
            onValueChange = onProductNameChange,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextField(
                modifier = Modifier.weight(1f),
                value = currentProduct.quantity.toString(),
                onValueChange = { text ->
                    onProductQuantityChange(text.filter { it.isDigit() }
                        .removeStartingTrailingZeros())
                },
                label = {
                    Text(text = LocalContext.current.getString(R.string.hint_product_quantity))
                },
                suffix = {
                    Text(text = LocalContext.current.getString(R.string.unit_abbrev))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = TextMaskTransformation(NumberAppearance::numberMaskFilter),
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                modifier = Modifier.weight(1f),
                value = currentProduct.product.unitPriceWithoutFloatingPoint.toString(),
                onValueChange = { text ->
                    val input = text.removeStartingTrailingZeros()

                    onProductPriceChange(input)
                },
                label = {
                    Text(text = LocalContext.current.getString(R.string.hint_product_quantity))
                },
                prefix = {
                    Text(text = LocalContext.current.getString(designSystemR.string.currency))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = TextMaskTransformation(NumberAppearance::currencyMaskFilter),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = onAddProduct, enabled = enableAddProductButton) {
                Text(text = LocalContext.current.getString(R.string.add_product))
            }
        }
    }
}

@Composable
private fun BottomSheetHeader(
    order: Order,
    showSave: Boolean,
    enableSave: Boolean,
    onSaveOrder: VoidListener
) {
    Text(
        text = "Total",
        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.outline)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DataDisplay(
            data = order.totalUnits,
            dataFormatter = { it.toText() },
            suffix = LocalContext.current.getString(R.string.unit_abbrev),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            extrasStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline),
        )
        DataDisplay(
            data = order.totalPrice,
            dataFormatter = { it.toCurrency() },
            prefix = LocalContext.current.getString(designSystemR.string.currency),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            extrasStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline),
        )
        AnimatedVisibility(visible = showSave) {
            Button(onClick = onSaveOrder, enabled = enableSave) {
                Text(text = LocalContext.current.getString(designSystemR.string.action_save))
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewAddProductBottomSheet() {
    AppTheme {
        AddProductBottomSheet(
            order = Order(
                id = 123,
                productList = listOf(
                    OrderProduct(
                        Product(
                            name = "name",
                            unitPrice = BigDecimal(12.5)
                        ), quantity = 15
                    )
                )
            ),
            showSave = true,
            enableSave = false,
            currentProduct = OrderProduct.Empty,
            enableAddProductButton = false,
            onProductPriceChange = {},
            onAddProduct = {},
            onProductNameChange = {},
            onProductQuantityChange = {},
            onSaveOrder = {}
        )
    }
}
