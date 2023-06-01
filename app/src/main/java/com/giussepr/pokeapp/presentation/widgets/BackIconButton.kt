package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BackIconButton(onNavigateUp: () -> Unit, iconColor: Color? = null) {
    IconButton(onClick = onNavigateUp) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = iconColor ?: LocalContentColor.current
        )
    }
}
