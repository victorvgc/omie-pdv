package com.victorvgc.order_screen.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.components.displays.DataDisplay
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.OrderProduct
import com.victorvgc.domain.core.Product
import com.victorvgc.utils.extensions.toCurrency
import java.math.BigDecimal

@Composable
fun ProductListItem(
    orderProduct: OrderProduct,
    onRemove: (OrderProduct) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = orderProduct.product.name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.outline)
                )
                Row {
                    Spacer(modifier = Modifier.width(8.dp))
                    DataDisplay(
                        data = orderProduct.product.unitPrice,
                        dataFormatter = { it.toCurrency() },
                        prefix = LocalContext.current.getString(R.string.currency),
                        textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outlineVariant),
                        extrasStyle = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outlineVariant)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Text(
                        text = LocalContext.current.getString(
                            R.string.currency_format,
                            orderProduct.totalPrice.toCurrency()
                        ),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
            ProductUnitSelector(orderProduct.quantity, onDelete = {
                onRemove(orderProduct)
            })
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewProductListItem() {
    AppTheme {
        Column {
            ProductListItem(
                orderProduct = OrderProduct(
                    product = Product(
                        name = "Product name 1",
                        unitPrice = BigDecimal(1)
                    ), quantity = 1000
                )
            ) {}
        }
    }
}
