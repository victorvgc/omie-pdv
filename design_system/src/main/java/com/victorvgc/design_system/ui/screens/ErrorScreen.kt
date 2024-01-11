package com.victorvgc.design_system.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorScreen(
    @DrawableRes icon: Int = 0,
    imageVector: ImageVector? = null,
    message: String,
    onRefreshClick: (() -> Unit)? = null,
    onBackPressed: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.errorContainer,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                title = {}, navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = LocalContext.current.getString(R.string.action_back)
                        )
                    }
                })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(40.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (icon != 0) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            } else if (imageVector != null) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.onErrorContainer)
            )
            if (onRefreshClick != null) {
                Spacer(modifier = Modifier.height(32.dp))
                TextButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = LocalContext.current.getString(R.string.action_refresh),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewErrorScreen() {
    AppTheme {
        ErrorScreen(message = "This is an error message that can be very long and complex") {}
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewErrorScreenWithIcon() {
    AppTheme {
        ErrorScreen(
            message = "This is an error message that can be very long and complex",
            icon = R.drawable.ic_clients_count
        ) {}
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewErrorScreenWithRefresh() {
    AppTheme {
        ErrorScreen(
            message = "This is an error message that can be very long and complex",
            onRefreshClick = {}
        ) {}
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewErrorScreenComplete() {
    AppTheme {
        ErrorScreen(
            message = "This is an error message that can be very long and complex",
            imageVector = Icons.Default.Warning,
            onRefreshClick = {}
        ) {}
    }
}
