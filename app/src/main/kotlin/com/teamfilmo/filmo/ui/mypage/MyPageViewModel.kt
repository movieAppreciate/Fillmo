package com.teamfilmo.filmo.ui.mypage

import androidx.lifecycle.viewModelScope
import com.teamfilmo.filmo.base.viewmodel.BaseViewModel
import com.teamfilmo.filmo.domain.follow.CountFollowUseCase
import com.teamfilmo.filmo.domain.report.GetReportListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val getReportListUseCase: GetReportListUseCase,
        private var followCountFollowUseCase: CountFollowUseCase,
    ) : BaseViewModel<MyPageEffect, MyPageEvent>() {
    /*
        팔로우 수
     */
        private val _followerCount = MutableStateFlow(0)
        val followerCount: StateFlow<Int> = _followerCount.asStateFlow()

    /*
     팔로잉 수
     */
        private val _followingCount = MutableStateFlow(0)
        val followingCount: StateFlow<Int> = _followingCount.asStateFlow()

    /*
    감상문 정보
     */

        fun getFollowCount(otherUserId: String) {
            viewModelScope.launch {
                followCountFollowUseCase(otherUserId = otherUserId).collect {
                    if (it != null) {
                        _followingCount.value = it.countFollowing
                    }
                    if (it != null) {
                        _followerCount.value = it.countFollower
                    }
                }
            }
        }
    }
