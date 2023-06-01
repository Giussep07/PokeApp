package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.giussepr.pokeapp.R

@Composable
@Preview
fun PokeAppTopAppBarPreview() {
    PokeAppTopAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeAppTopAppBar(
    title: String = stringResource(id = R.string.app_name),
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color? = null,
    titleColor: Color? = null
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor ?: MaterialTheme.colorScheme.surface,
            titleContentColor = titleColor ?: MaterialTheme.colorScheme.onSurface
        )
    )
}
