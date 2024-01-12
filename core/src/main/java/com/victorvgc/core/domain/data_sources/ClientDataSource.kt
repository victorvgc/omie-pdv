package com.victorvgc.core.domain.data_sources

import com.victorvgc.domain.core.Client
import com.victorvgc.utils.result_helper.ResultWrapper

interface ClientDataSource {
    suspend fun createClient(client: Client): ResultWrapper<Unit>

    suspend fun deleteClient(client: Client): ResultWrapper<Unit>

    suspend fun getAllClients(): ResultWrapper<List<Client>>

    suspend fun getClient(clientName: String): ResultWrapper<Client>
}
