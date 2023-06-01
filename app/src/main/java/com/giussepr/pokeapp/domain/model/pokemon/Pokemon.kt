package com.giussepr.pokeapp.domain.model.pokemon

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<PokemonType> = emptyList()
) {
    fun getIdFormatted(): String {
        return String.format("#%03d", id)
    }
}
