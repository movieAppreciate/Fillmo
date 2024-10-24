package com.teamfilmo.filmo.data.remote.model.user

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
)
