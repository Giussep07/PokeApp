package com.giussepr.pokeapp.presentation.screens.pokemondetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giussepr.pokeapp.R
import com.giussepr.pokeapp.domain.model.pokemon.Pokemon
import com.giussepr.pokeapp.domain.model.pokemon.PokemonType
import com.giussepr.pokeapp.domain.model.pokemon.PokemonTypeAsset
import com.giussepr.pokeapp.presentation.theme.PokeAppTheme
import com.giussepr.pokeapp.presentation.widgets.BackIconButton
import com.giussepr.pokeapp.presentation.widgets.PokeAppTopAppBar

@Composable
@Preview
fun PokemonDetailScreenPreview() {
    PokeAppTheme {
        PokemonDetailScreen(
            pokemon = Pokemon(
                1,
                "Bulbasaur",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                listOf(
                    PokemonType(1, "Grass", PokemonTypeAsset.Grass),
                    PokemonType(2, "Poison", PokemonTypeAsset.Poison)
                )
            ),
            onNavigateUp = {}
        )
    }
}

@Composable
fun PokemonDetailScreen(pokemon: Pokemon? = null, onNavigateUp: () -> Unit) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    pokemon?.types?.firstOrNull()?.asset?.backgroundColor ?: Color.LightGray
                )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize(unbounded = true, align = Alignment.CenterEnd)
                .size(240.dp)
                .alpha(0.1f),
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PokeAppTopAppBar(
                title = pokemon?.name ?: "",
                navigationIcon = {
                    BackIconButton(onNavigateUp = onNavigateUp, iconColor = Color.White)
                },
                backgroundColor = pokemon?.types?.firstOrNull()?.asset?.backgroundColor
                    ?: Color.LightGray,
                titleColor = Color.White
            )
            PokemonDetailTitle(pokemon = pokemon)
            PokemonDetailImage(pokemon = pokemon)
            PokemonDetailTabContent()
        }
    }
}

@Composable
fun PokemonDetailTitle(pokemon: Pokemon?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pokemon?.name ?: "",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            PokemonDetailTypes(pokemon = pokemon)
        }
        Text(
            text = pokemon?.getIdFormatted() ?: "",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailTypes(pokemon: Pokemon?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        pokemon?.types?.forEach {
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

@Composable
fun PokemonDetailImage(pokemon: Pokemon?) {
    AsyncImage(
        modifier = Modifier.size(240.dp),
        model = ImageRequest.Builder(LocalContext.current).data(pokemon?.imageUrl)
            .crossfade(true).build(),
        placeholder = painterResource(id = R.drawable.ic_pokeball),
        contentDescription = null
    )
}

@Composable
fun PokemonDetailTabContent() {
    var tabIndex by remember {
        mutableStateOf(0)
    }

    val tabs = listOf("About", "Base Stats", "Moves")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            )
            .padding(16.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            contentColor = Color.Black.copy(alpha = 0.8f),
            containerColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.Black.copy(alpha = 0.8f),
                    height = 2.dp
                )
            },
            divider = {
                Divider(color = Color.Black.copy(alpha = 0.2f))
            },
            tabs = {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        selectedContentColor = Color.Black.copy(alpha = 0.8f),
                        unselectedContentColor = Color.Black.copy(alpha = 0.6f),
                    )
                }
            })
        when (tabIndex) {
            0 -> PokemonAboutScreen()
            1 -> PokemonBaseStatsScreen()
            2 -> PokemonMovesScreen()
        }
    }
}
