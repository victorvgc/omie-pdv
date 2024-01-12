package com.victorvgc.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.victorvgc.dashboard.R
import com.victorvgc.dashboard.domain.models.DashboardFilter
import com.victorvgc.design_system.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMenu(
    selected: DashboardFilter,
    onSelectFilter: (DashboardFilter) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            FilterChip(selected = selected == DashboardFilter.DAY, onClick = {
                onSelectFilter(DashboardFilter.DAY)
            }, label = {
                Text(text = LocalContext.current.getString(R.string.filter_day))
            })
        }
        item {
            FilterChip(selected = selected == DashboardFilter.MONTH, onClick = {
                onSelectFilter(DashboardFilter.MONTH)
            }, label = {
                Text(text = LocalContext.current.getString(R.string.filter_month))
            })
        }
        item {
            FilterChip(selected = selected == DashboardFilter.YEAR, onClick = {
                onSelectFilter(DashboardFilter.YEAR)
            }, label = {
                Text(text = LocalContext.current.getString(R.string.filter_year))
            })
        }
        item {
            FilterChip(selected = selected == DashboardFilter.ALL, onClick = {
                onSelectFilter(DashboardFilter.ALL)
            }, label = {
                Text(text = LocalContext.current.getString(R.string.filter_all))
            })
        }
    }
}

@PreviewLightDark
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewFilterMenu() {
    AppTheme {
        Column {
            FilterMenu(
                selected = DashboardFilter.ALL,
                onSelectFilter = {}
            )
        }
    }
}
