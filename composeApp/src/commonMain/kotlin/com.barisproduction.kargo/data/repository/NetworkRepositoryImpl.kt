package com.barisproduction.kargo.data.repository

import com.barisproduction.kargo.common.Resource
import com.barisproduction.kargo.data.util.ErrorParser
import com.barisproduction.kargo.domain.repository.NetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.head
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val client: HttpClient
) : NetworkRepository {
    override suspend fun isInternetAvailable(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            //head getten hafif sadece başık okur
            val response = client.head("https://8.8.8.8")
            if (response.status.value == 200) emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(ErrorParser.parse(e))
        }
    }
}
