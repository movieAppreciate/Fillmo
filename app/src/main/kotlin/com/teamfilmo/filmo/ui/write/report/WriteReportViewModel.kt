package com.teamfilmo.filmo.ui.write.report

import androidx.lifecycle.viewModelScope
import com.teamfilmo.filmo.base.viewmodel.BaseViewModel
import com.teamfilmo.filmo.data.remote.model.report.RegistReportRequest
import com.teamfilmo.filmo.domain.report.RegistReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class WriteReportViewModel
    @Inject
    constructor(
        private val registReportUseCase: RegistReportUseCase,
    ) : BaseViewModel<WriteReportEffect, WriteReportEvent>() {
        override fun handleEvent(event: WriteReportEvent) {
            when (event) {
                is WriteReportEvent.RegisterReport -> {
                    registerReport(event.loginId, event.request)
                    sendEffect(WriteReportEffect.NavigateToMain)
                }
            }
        }

        private fun registerReport(
            loginId: String = "tjdgustjdan@gmail.com",
            request: RegistReportRequest,
        ) {
            viewModelScope.launch {
                registReportUseCase(loginId, request).collect {
                    Timber.d("regist : $it")
                }
            }
        }
    }
