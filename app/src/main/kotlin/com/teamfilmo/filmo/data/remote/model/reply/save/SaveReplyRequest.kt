package com.teamfilmo.filmo.data.remote.model.reply.save

import kotlinx.serialization.Serializable

@Serializable
data class SaveReplyRequest(
    val upReplyId: String? = null,
    val reportId: String,
    val content: String,
)
