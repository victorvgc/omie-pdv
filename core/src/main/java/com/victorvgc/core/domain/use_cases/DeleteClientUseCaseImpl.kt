package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ClientRepository
import com.victorvgc.domain.core.Client
import javax.inject.Inject

class DeleteClientUseCaseImpl @Inject constructor(
    private val clientRepository: ClientRepository
) : DeleteClientUseCase {
    override suspend fun invoke(client: Client) = clientRepository.deleteClient(client)
}
