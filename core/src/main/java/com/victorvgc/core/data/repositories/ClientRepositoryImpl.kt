package com.victorvgc.core.data.repositories

import com.victorvgc.core.domain.data_sources.ClientDataSource
import com.victorvgc.core.domain.repositories.ClientRepository
import com.victorvgc.domain.core.Client
import javax.inject.Inject

class ClientRepositoryImpl @Inject constructor(
    private val clientDataSource: ClientDataSource
) : ClientRepository {
    override suspend fun createClient(client: Client) = clientDataSource.createClient(client)

    override suspend fun deleteClient(client: Client) = clientDataSource.deleteClient(client)

    override suspend fun getAllClients() = clientDataSource.getAllClients()

    override suspend fun getClient(clientName: String) = clientDataSource.getClient(clientName)
}
