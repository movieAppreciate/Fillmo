package com.teamfilmo.filmo.data.remote.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class MovieRequest(
    val query: String,
    val page: Int,
)

@Serializable
data class ThumbnailRequest(
    val movieId: String,
)
