package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PokemonDataItemTitlePreview() {
    PokemonDataItemTitle("Breeding")
}

@Composable
fun PokemonDataItemTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(vertical = 24.dp),
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
    )
}
