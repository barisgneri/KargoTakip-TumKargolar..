package com.barisproduction.kargo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.delegation.MVI
import com.barisproduction.kargo.delegation.mvi
import com.barisproduction.kargo.domain.repository.CargoRepository
import com.barisproduction.kargo.ui.splash.SplashContract.UiAction
import com.barisproduction.kargo.ui.splash.SplashContract.UiEffect
import com.barisproduction.kargo.ui.splash.SplashContract.UiState
import com.barisproduction.kargo.domain.usecase.CheckNetworkUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkNetworkUseCase: CheckNetworkUseCase,
    private val cargoRepository: CargoRepository,
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
            if (isConnected) {
                getParcelList()
            } else {
                updateUiState { copy(isError = true) }
            }
        }
    }

    private fun getParcelList() {
        viewModelScope.launch {
            cargoRepository.getCargoParcelList()
            delay(1000)
            cargoRepository.getCargoParcelListState().collect {
                when (it) {
                    is Resource.Success -> {
                        println(it.data)
                        updateUiState { copy( isError = false) }
                        emitUiEffect(UiEffect.NavigateToMain)
                    }

                    is Resource.Error -> {
                        updateUiState { copy(isLoading = false, isError = true) }
                    }

                    is Resource.Loading -> {
                        updateUiState { copy(isLoading = true, isError = false) }
                    }
                }
            }
        }
    }
}