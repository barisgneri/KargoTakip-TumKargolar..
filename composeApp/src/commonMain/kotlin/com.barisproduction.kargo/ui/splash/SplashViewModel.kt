package com.barisproduction.kargo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.domain.usecase.CheckNetworkUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkNetworkUseCase: CheckNetworkUseCase
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        checkNetwork()
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when(uiAction){
                UiAction.CheckNetwork -> checkNetwork()
            }
        }
    }

    private fun checkNetwork() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true, isError = false) }
            val isConnected = checkNetworkUseCase()
            updateUiState { copy(isLoading = false) }
            
            if (isConnected) {
                updateUiState { copy(isError = false) }
                emitUiEffect(UiEffect.NavigateToMain)
            } else {
                updateUiState { copy(isError = true) }
            }
        }
    }
}