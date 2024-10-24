package com.teamfilmo.filmo.data.repository

import com.teamfilmo.filmo.data.remote.model.like.SaveLikeRequest
import com.teamfilmo.filmo.data.remote.model.like.SaveLikeResponse
import com.teamfilmo.filmo.data.source.LikeDataSource
import com.teamfilmo.filmo.domain.repository.LikeRepository
import javax.inject.Inject

class LikeRepositoryImpl
    @Inject
    constructor(
        private val likeDataSource: LikeDataSource,
    ) : LikeRepository {
        override suspend fun saveLike(saveLikeRequest: SaveLikeRequest): Result<SaveLikeResponse> = likeDataSource.saveLike(saveLikeRequest)

        override suspend fun checkLike(
            targetId: String,
            type: String,
        ): Result<Boolean> = likeDataSource.checkLike(targetId, type)

        override suspend fun cancleLike(reportId: String): Result<String> = likeDataSource.cancelLike(reportId)

        override suspend fun countLike(reportId: String): Result<Int> = likeDataSource.countLike(reportId)
    }
