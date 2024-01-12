package com.victorvgc.order_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.ui.theme.AppTheme
import com.victorvgc.domain.core.Order
import com.victorvgc.order_screen.R
import com.victorvgc.order_screen.domain.models.OrderScreenState
import com.victorvgc.utils.viewmodel.ScreenState

@Composable
fun SaveOrderConfirmation(
    state: ScreenState.Success<OrderScreenState>
) {
    SaveOrderConfirmationScreen(
        state = state.data,
    )
}

@Composable
private fun SaveOrderConfirmationScreen(
    state: OrderScreenState
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = LocalContext.current.getString(R.string.order_saved_success),
                style = MaterialTheme.typography.headlineSmall.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            )
            Text(
                text = LocalContext.current.getString(
                    R.string.order_saved_success_1,
                    state.order.id.toString()
                ),
                style = MaterialTheme.typography.headlineSmall.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = LocalContext.current.getString(R.string.order_saved_success_2),
                style = MaterialTheme.typography.headlineSmall.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline
                )
            )
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewSaveOrderConfirmationScreen() {
    AppTheme {
        SaveOrderConfirmationScreen(
            state = OrderScreenState(
                order = Order.Empty
            )
        )
    }
}
