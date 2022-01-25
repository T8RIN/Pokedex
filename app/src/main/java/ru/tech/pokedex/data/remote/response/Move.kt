package ru.tech.pokedex.data.remote.response

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)