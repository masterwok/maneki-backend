package dev.maneki.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val error: String? = null,
) {
    companion object
}
