package com.barisproduction.kargo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.extensions.toUserMessage
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.Platform
import com.barisproduction.kargo.domain.model.ForceUpdateDecision
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.domain.usecase.CheckForceUpdateUseCase
import com.barisproduction.kargo.domain.usecase.FetchCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SplashViewModel(
    private val fetchCargoParcelListUseCase: FetchCargoParcelListUseCase,
    private val getCargoParcelListUseCase: GetCargoParcelListUseCase,
    private val checkForceUpdateUseCase: CheckForceUpdateUseCase,
    private val platform: Platform
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        viewModelScope.launch {
            runSplashFlow()
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.Retry -> runSplashFlow()
                UiAction.OnUpdateClick -> {
                    uiState.value.storeUrl?.let { emitUiEffect(UiEffect.NavigateToStore(it)) }
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun runSplashFlow() {
        val startTime = Clock.System.now().toEpochMilliseconds()

        updateUiState {
            copy(
                isLoading = true,
                errorMessage = null,
                showUpdateDialog = false,
                storeUrl = null
            )
        }

        val forceUpdateDecision = checkForceUpdate()
        if (forceUpdateDecision?.isRequired == true) return

        if (getParcelList()) {
            val elapsedTime = Clock.System.now().toEpochMilliseconds() - startTime
            val remainingTime = 3000L - elapsedTime
            if (remainingTime > 0) {
                delay(remainingTime)
            }
            emitUiEffect(UiEffect.NavigateToMain)
        }
    }

    private suspend fun checkForceUpdate(): ForceUpdateDecision? {
        when (
            val updateResult = checkForceUpdateUseCase(
                platformName = platform.name,
                currentVersionCode = platform.versionCode
            )
        ) {
            is Resource.Success -> {
                val decision = updateResult.data
                if (decision?.isRequired == true && decision.storeUrl != null) {
                    updateUiState {
                        copy(
                            isLoading = false,
                            showUpdateDialog = true,
                            storeUrl = decision.storeUrl
                        )
                    }
                    return decision
                } else {
                    return decision
                }
            }
            is Resource.Error -> {
                return null
            }
            is Resource.Loading -> return null
        }
    }

    private suspend fun getParcelList(): Boolean {
        fetchCargoParcelListUseCase()
        return when (val result = getCargoParcelListUseCase().first { it !is Resource.Loading }) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false) }
                true
            }

            is Resource.Error -> {
                val errorMessage = result.errorType.toUserMessage()
                updateUiState { copy(isLoading = false, errorMessage = errorMessage) }
                false
            }

            else -> false
        }
    }
}
