package com.barisproduction.kargo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.common.extensions.toUserMessage
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.domain.usecase.CheckNetworkUseCase
import com.barisproduction.kargo.domain.usecase.FetchCargoParcelListUseCase
import com.barisproduction.kargo.domain.usecase.GetCargoParcelListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkNetworkUseCase: CheckNetworkUseCase,
    private val fetchCargoParcelListUseCase: FetchCargoParcelListUseCase,
    private val getCargoParcelListUseCase: GetCargoParcelListUseCase,
) : ViewModel(), MVI<UiState, UiAction, UiEffect> by mvi(UiState()) {

    init {
        viewModelScope.launch {
            checkNetwork()
        }
    }

    override fun onAction(uiAction: UiAction) {
        viewModelScope.launch {
            when (uiAction) {
                UiAction.CheckNetwork -> checkNetwork()
                UiAction.OnUpdateClick -> {}
                UiAction.OnDismissUpdateDialog -> {}
            }
        }
    }

    private suspend fun checkNetwork() {
        checkNetworkUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == true) {
                        getParcelList()
                    }
                }

                is Resource.Error -> {
                    updateUiState {
                        copy(
                            isLoading = false,
                            errorMessage = result.errorType.toUserMessage()
                        )
                    }
                }

                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true, errorMessage = null) }
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getParcelList() {
        fetchCargoParcelListUseCase()
        delay(1000)
        getCargoParcelListUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    updateUiState { copy(isLoading = true) }
                }

                is Resource.Success -> {
                    updateUiState {
                        copy(
                            isLoading = false
                        )
                    }
                    emitUiEffect(UiEffect.NavigateToMain)
                }

                is Resource.Error -> {
                    val errorMessage = result.errorType.toUserMessage()
                    updateUiState { copy(isLoading = false, errorMessage = errorMessage) }
                }
            }
        }.launchIn(viewModelScope)
    }
}
