package com.teamfilmo.filmo.domain.repository

import com.teamfilmo.filmo.data.remote.model.user.LoginRequest
import com.teamfilmo.filmo.data.remote.model.user.LoginResponse
import com.teamfilmo.filmo.data.remote.model.user.SignUpResponse

interface AuthRepository {
    suspend fun signUp(
        uid: String,
        type: String,
        profileURL: String?,
    ): Result<SignUpResponse>

    suspend fun login(
        loginRequest: LoginRequest,
    ): Result<LoginResponse>
}
