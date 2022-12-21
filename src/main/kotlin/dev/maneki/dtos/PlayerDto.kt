package dev.maneki.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val playerNumber: Int,
    val fullName: String,
)
