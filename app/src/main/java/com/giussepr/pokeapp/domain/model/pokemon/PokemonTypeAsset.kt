package com.giussepr.pokeapp.domain.model.pokemon

import androidx.compose.ui.graphics.Color
import com.giussepr.pokeapp.presentation.theme.BackgroundBug
import com.giussepr.pokeapp.presentation.theme.BackgroundDark
import com.giussepr.pokeapp.presentation.theme.BackgroundDragon
import com.giussepr.pokeapp.presentation.theme.BackgroundElectric
import com.giussepr.pokeapp.presentation.theme.BackgroundFairy
import com.giussepr.pokeapp.presentation.theme.BackgroundFighting
import com.giussepr.pokeapp.presentation.theme.BackgroundFire
import com.giussepr.pokeapp.presentation.theme.BackgroundFlying
import com.giussepr.pokeapp.presentation.theme.BackgroundGhost
import com.giussepr.pokeapp.presentation.theme.BackgroundGrass
import com.giussepr.pokeapp.presentation.theme.BackgroundGround
import com.giussepr.pokeapp.presentation.theme.BackgroundIce
import com.giussepr.pokeapp.presentation.theme.BackgroundNormal
import com.giussepr.pokeapp.presentation.theme.BackgroundPoison
import com.giussepr.pokeapp.presentation.theme.BackgroundPsychic
import com.giussepr.pokeapp.presentation.theme.BackgroundRock
import com.giussepr.pokeapp.presentation.theme.BackgroundSteel
import com.giussepr.pokeapp.presentation.theme.BackgroundWater
import com.giussepr.pokeapp.presentation.theme.TypeBug
import com.giussepr.pokeapp.presentation.theme.TypeDark
import com.giussepr.pokeapp.presentation.theme.TypeDragon
import com.giussepr.pokeapp.presentation.theme.TypeElectric
import com.giussepr.pokeapp.presentation.theme.TypeFairy
import com.giussepr.pokeapp.presentation.theme.TypeFighting
import com.giussepr.pokeapp.presentation.theme.TypeFire
import com.giussepr.pokeapp.presentation.theme.TypeFlying
import com.giussepr.pokeapp.presentation.theme.TypeGhost
import com.giussepr.pokeapp.presentation.theme.TypeGrass
import com.giussepr.pokeapp.presentation.theme.TypeGround
import com.giussepr.pokeapp.presentation.theme.TypeIce
import com.giussepr.pokeapp.presentation.theme.TypeNormal
import com.giussepr.pokeapp.presentation.theme.TypePoison
import com.giussepr.pokeapp.presentation.theme.TypePsychic
import com.giussepr.pokeapp.presentation.theme.TypeRock
import com.giussepr.pokeapp.presentation.theme.TypeSteel
import com.giussepr.pokeapp.presentation.theme.TypeWater

sealed class PokemonTypeAsset(
    val typeColor: Color,
    val backgroundColor: Color = Color.White
) {
    object Bug : PokemonTypeAsset(
        typeColor = TypeBug,
        backgroundColor = BackgroundBug
    )

    object Dark : PokemonTypeAsset(
        typeColor = TypeDark,
        backgroundColor = BackgroundDark
    )

    object Dragon : PokemonTypeAsset(
        typeColor = TypeDragon,
        backgroundColor = BackgroundDragon
    )

    object Electric : PokemonTypeAsset(
        typeColor = TypeElectric,
        backgroundColor = BackgroundElectric
    )

    object Fairy : PokemonTypeAsset(
        typeColor = TypeFairy,
        backgroundColor = BackgroundFairy
    )

    object Fighting : PokemonTypeAsset(
        typeColor = TypeFighting,
        backgroundColor = BackgroundFighting
    )

    object Fire : PokemonTypeAsset(
        typeColor = TypeFire,
        backgroundColor = BackgroundFire
    )

    object Flying : PokemonTypeAsset(
        typeColor = TypeFlying,
        backgroundColor = BackgroundFlying
    )

    object Ghost : PokemonTypeAsset(
        typeColor = TypeGhost,
        backgroundColor = BackgroundGhost
    )

    object Grass : PokemonTypeAsset(
        typeColor = TypeGrass,
        backgroundColor = BackgroundGrass
    )

    object Ground : PokemonTypeAsset(
        typeColor = TypeGround,
        backgroundColor = BackgroundGround
    )

    object Ice : PokemonTypeAsset(
        typeColor = TypeIce,
        backgroundColor = BackgroundIce
    )

    object Normal : PokemonTypeAsset(
        typeColor = TypeNormal,
        backgroundColor = BackgroundNormal
    )

    object Poison : PokemonTypeAsset(
        typeColor = TypePoison,
        backgroundColor = BackgroundPoison
    )

    object Psychic : PokemonTypeAsset(
        typeColor = TypePsychic,
        backgroundColor = BackgroundPsychic
    )

    object Rock : PokemonTypeAsset(
        typeColor = TypeRock,
        backgroundColor = BackgroundRock
    )

    object Steel : PokemonTypeAsset(
        typeColor = TypeSteel,
        backgroundColor = BackgroundSteel
    )

    object Water : PokemonTypeAsset(
        typeColor = TypeWater,
        backgroundColor = BackgroundWater
    )

    companion object {
        fun getAsset(type: String): PokemonTypeAsset {
            return when (type) {
                "bug" -> Bug
                "dark" -> Dark
                "dragon" -> Dragon
                "electric" -> Electric
                "fairy" -> Fairy
                "fighting" -> Fighting
                "fire" -> Fire
                "flying" -> Flying
                "ghost" -> Ghost
                "grass" -> Grass
                "ground" -> Ground
                "ice" -> Ice
                "normal" -> Normal
                "poison" -> Poison
                "psychic" -> Psychic
                "rock" -> Rock
                "steel" -> Steel
                "water" -> Water
                else -> Dark
            }
        }
    }
}
