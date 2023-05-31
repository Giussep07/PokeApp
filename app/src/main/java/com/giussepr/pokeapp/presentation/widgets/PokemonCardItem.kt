@file:OptIn(ExperimentalMaterial3Api::class)

package com.giussepr.pokeapp.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giussepr.pokeapp.R
import com.giussepr.pokeapp.domain.model.ListViewType
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.pokemon.PokemonType
import com.giussepr.pokeapp.domain.model.pokemon.PokemonTypeAsset
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme


@Composable
@Preview
fun PokemonCardItemPreview() {
    PokeAppTheme {
        PokemonCardItemRow(
            Pokemon(
                1,
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                listOf(
                    PokemonType(1, "Grass", PokemonTypeAsset.Grass),
                    PokemonType(2, "Poison", PokemonTypeAsset.Poison)
                )
            )
        )
    }
}

@Composable
@Preview(widthDp = 200)
fun PokemonCardItemColumnPreview() {
    PokeAppTheme {
        PokemonCardItemColumn(
            Pokemon(
                1,
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                listOf(
                    PokemonType(1, "Grass", PokemonTypeAsset.Grass),
                    PokemonType(2, "Poison", PokemonTypeAsset.Poison)
                )
            )
        )
    }
}

@Composable
fun PokemonCardItem(pokemon: Pokemon, listViewType: ListViewType = ListViewType.LIST) {
    if (listViewType == ListViewType.LIST) {
        PokemonCardItemRow(pokemon = pokemon)
    } else {
        PokemonCardItemColumn(pokemon = pokemon)
    }
}

@Composable
fun PokemonCardItemRow(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(152.dp),
        colors = CardDefaults.cardColors(
            containerColor = pokemon.types.first().asset.backgroundColor
        )
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_pokeball),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
                    .alpha(0.1f)
                    .align(Alignment.CenterEnd),
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = pokemon.getIdFormatted(),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White,
                    )
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        pokemon.types.forEach {
                            AssistChip(
                                onClick = { /*TODO*/ },
                                border = null,
                                label = {
                                    Text(
                                        text = it.name,
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = it.asset.typeColor.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }
                Column {
                    PokemonCardImage(pokemon = pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonCardItemColumn(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = pokemon.types.first().asset.backgroundColor
        )
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_pokeball),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
                    .alpha(0.1f)
                    .align(Alignment.CenterEnd),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = pokemon.getIdFormatted(),
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                SmallPokemonCardImage(pokemon = pokemon)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    pokemon.types.forEach {
                        AssistChip(
                            onClick = { /*TODO*/ },
                            border = null,
                            label = {
                                Text(
                                    text = it.name,
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = it.asset.typeColor.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCardImage(pokemon: Pokemon) {
    AsyncImage(
        modifier = Modifier.fillMaxHeight(),
        model = ImageRequest.Builder(LocalContext.current).data(pokemon.imageUrl)
            .crossfade(true).build(),
        contentDescription = null
    )
}

@Composable
fun SmallPokemonCardImage(pokemon: Pokemon) {
    AsyncImage(
        modifier = Modifier.fillMaxWidth(),
        model = ImageRequest.Builder(LocalContext.current).data(pokemon.imageUrl)
            .crossfade(true).build(),
        placeholder = painterResource(id = R.drawable.ic_pokeball),
        contentDescription = null
    )
}
