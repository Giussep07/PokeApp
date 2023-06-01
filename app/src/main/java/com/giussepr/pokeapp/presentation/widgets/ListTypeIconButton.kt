package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.giussepr.pokeapp.R

@Composable
fun ListTypeIconButton(onListTypeClicked: () -> Unit) {
    IconButton(onClick = onListTypeClicked) {
        Icon(
            painter = painterResource(id = R.drawable.ic_grid_view),
            contentDescription = null
        )
    }
}
