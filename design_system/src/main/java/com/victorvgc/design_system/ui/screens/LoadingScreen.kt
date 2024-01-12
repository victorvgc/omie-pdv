package com.victorvgc.design_system.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.ui.theme.AppTheme

@Composable
fun LoadingScreen(
    infiniteLoading: Boolean = true,
    loadingProgress: Float = 0f
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (infiniteLoading) {
                CircularProgressIndicator()
            } else {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(.5f),
                    progress = loadingProgress
                )
            }
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewInfiniteLoadingScreen() {
    AppTheme {
        LoadingScreen()
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewProgressLoadingScreen() {
    AppTheme {
        LoadingScreen(infiniteLoading = false, loadingProgress = 0.8f)
    }
}
