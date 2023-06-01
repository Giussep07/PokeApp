package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.giussepr.pokeapp.R
import com.giussepr.pokeapp.presentation.theme.FemaleGenderColor
import com.giussepr.pokeapp.presentation.theme.MaleGenderColor
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme

@Preview
@Composable
fun PokemonDataItemPreview() {
    PokeAppTheme {
        PokemonDataItemDefaults.PokemonDataItem("Height", "70 cm")
    }
}

@Preview
@Composable
fun PokemonDataItemGenderPreview() {
    PokeAppTheme {
        PokemonDataItemDefaults.PokemonDataItemGender(genderRate = 1)
    }
}

object PokemonDataItemDefaults {

    private const val titleWeight = 0.4f

    @Composable
    fun PokemonDataItem(title: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .weight(titleWeight),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Text(
                text = value,
                modifier = Modifier
                    .weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    @Composable
    fun PokemonDataItemGender(genderRate: Int) {
        val femaleRate: Double = (genderRate.toDouble() / 8) * 100
        val maleRate: Double = 100 - femaleRate

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.gender),
                modifier = Modifier
                    .weight(titleWeight),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_female),
                    contentDescription = null,
                    tint = FemaleGenderColor
                )
                Text(
                    text = "$femaleRate%",
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(24.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_male),
                    contentDescription = null,
                    tint = MaleGenderColor
                )
                Text(
                    text = "$maleRate%",
                    modifier = Modifier
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
