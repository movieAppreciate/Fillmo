package com.teamfilmo.filmo.domain.repository

import com.teamfilmo.filmo.data.remote.model.movie.MovieRequest
import com.teamfilmo.filmo.data.remote.model.movie.MovieResponse
import com.teamfilmo.filmo.data.remote.model.movie.PosterResponse
import com.teamfilmo.filmo.data.remote.model.movie.ThumbnailRequest
import com.teamfilmo.filmo.data.remote.model.movie.detail.DetailMovieRequest
import com.teamfilmo.filmo.data.remote.model.movie.detail.response.DetailMovieResponse

interface MovieRepository {
    suspend fun searchList(
        query: MovieRequest?,
    ): Result<MovieResponse>

    suspend fun searchDetail(movieId: DetailMovieRequest): Result<DetailMovieResponse>

    suspend fun getVideo(movieId: Int): Result<String>

    suspend fun getPoster(movieId: ThumbnailRequest): Result<PosterResponse>
}
