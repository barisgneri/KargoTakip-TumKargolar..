package com.barisproduction.kargo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.extensions.toUserMessage
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.getPlatform
import com.barisproduction.kargo.domain.model.ForceUpdateDecision
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.domain.usecase.CheckForceUpdateUseCase
import com.barisproduction.kargo.domain.usecase.FetchCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashViewModel(
    private val fetchCargoParcelListUseCase: FetchCargoParcelListUseCase,
    private val getCargoParcelListUseCase: GetCargoParcelListUseCase,
    private val checkForceUpdateUseCase: CheckForceUpdateUseCase
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

    private suspend fun runSplashFlow() {
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

        getParcelList()
    }

    private suspend fun checkForceUpdate(): ForceUpdateDecision? {
        when (
            val updateResult = checkForceUpdateUseCase(
                platformName = getPlatform().name,
                currentVersionCode = getPlatform().versionCode
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

    private suspend fun getParcelList() {
        fetchCargoParcelListUseCase()
        when (val result = getCargoParcelListUseCase().first { it !is Resource.Loading }) {
            is Resource.Success -> {
                updateUiState { copy(isLoading = false) }
                emitUiEffect(UiEffect.NavigateToMain)
            }
            is Resource.Error -> {
                val errorMessage = result.errorType.toUserMessage()
                updateUiState { copy(isLoading = false, errorMessage = errorMessage) }
            }
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
        }
    }
}
