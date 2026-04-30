package com.barisproduction.kargo.domain.usecase

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.domain.model.ForceUpdateDecision
import com.barisproduction.kargo.domain.repository.CargoRepository

class CheckForceUpdateUseCase(
    private val cargoRepository: CargoRepository
) {
    suspend operator fun invoke(
        platformName: String,
        currentVersionCode: Int
    ): Resource<ForceUpdateDecision> {
        return when (val result = cargoRepository.getAppUpdateConfig()) {
            is Resource.Success -> {
                val config = result.data ?: return Resource.Success(
                    ForceUpdateDecision(
                        isRequired = false,
                        storeUrl = null
                    )
                )

                if (!config.requireUpdate) {
                    return Resource.Success(
                        ForceUpdateDecision(
                            isRequired = false,
                            storeUrl = null
                        )
                    )
                }

                val isIos = platformName.contains("ios", ignoreCase = true)
                val minimumBuildCode = if (isIos) config.iosMinBuildCode else config.androidMinBuildCode
                val storeUrl = if (isIos) config.iosStoreUrl else config.androidStoreUrl
                val shouldForceUpdate = currentVersionCode < minimumBuildCode && storeUrl.isNotBlank()

                Resource.Success(
                    ForceUpdateDecision(
                        isRequired = shouldForceUpdate,
                        storeUrl = storeUrl.takeIf { shouldForceUpdate }
                    )
                )
            }
            is Resource.Error -> Resource.Error(result.errorType)
            is Resource.Loading -> Resource.Loading()
        }
    }
}
