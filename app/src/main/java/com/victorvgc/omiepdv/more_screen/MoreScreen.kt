package com.victorvgc.omiepdv.more_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.omiepdv.R
import com.victorvgc.utils.VoidListener
import com.victorvgc.design_system.R as designSystemR


@Composable
fun MoreScreen(
    modifier: Modifier,
    onClickOrderList: VoidListener,
    onClickClientList: VoidListener,
    onClickProductList: VoidListener,
) {
    Column(modifier = modifier.padding(16.dp)) {
        MoreItem(
            text = LocalContext.current.getString(R.string.order_list),
            icon = designSystemR.drawable.ic_items_sold,
            onClick = onClickOrderList,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        MoreItem(
            text = LocalContext.current.getString(R.string.client_list),
            icon = designSystemR.drawable.ic_clients_count,
            onClick = {},
            color = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        MoreItem(
            text = LocalContext.current.getString(R.string.product_list),
            icon = designSystemR.drawable.ic_products_count,
            onClick = {},
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun MoreItem(text: String, @DrawableRes icon: Int, color: Color, onClick: VoidListener) {
    Row(
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            tint = color
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall.copy(color = color)
        )
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewMoreScreen() {
    AppTheme {
        Scaffold {
            MoreScreen(modifier = Modifier.padding(it), onClickOrderList = {}, {}) {


            }
        }
    }
}
