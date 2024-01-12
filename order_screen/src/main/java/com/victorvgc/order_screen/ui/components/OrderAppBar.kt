package com.victorvgc.order_screen.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.design_system.R
import com.victorvgc.design_system.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isEdit: Boolean,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val title = if (isEdit) {
        LocalContext.current.getString(com.victorvgc.order_screen.R.string.edit_order_page_title)
    } else {
        LocalContext.current.getString(com.victorvgc.order_screen.R.string.add_order_page_title)
    }

    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = LocalContext.current.getString(R.string.action_back)
                )
            }
        },
        actions = {
            AnimatedVisibility(visible = isEdit) {
                IconButton(onClick = {
                    onDeleteClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = LocalContext.current.getString(R.string.action_discard_changes)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewOderAppBar() {
    AppTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            topBar = {
                OrderAppBar(
                    scrollBehavior = scrollBehavior,
                    isEdit = false,
                    onBackClick = {},
                    onDeleteClick = {})
            }
        ) {
            Column(modifier = Modifier.padding(it)) {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewOderAppBarDelete() {
    AppTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        Scaffold(
            topBar = {
                OrderAppBar(
                    scrollBehavior = scrollBehavior,
                    isEdit = true,
                    onBackClick = {},
                    onDeleteClick = {},
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {

            }
        }
    }
}
