package dev.maneki.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T : Any>(
    val data: T? = null,
    val error: String? = null,
) {
    companion object
}

fun ApiResponse.Companion.error(error: String): ApiResponse<Unit> = ApiResponse(error = error)

inline fun <reified T : Any> ApiResponse.Companion.success(data: T): ApiResponse<T> = ApiResponse(data = data)
