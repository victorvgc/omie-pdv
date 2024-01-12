package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ClientRepository
import javax.inject.Inject

class GetAllClientsUseCaseImpl @Inject constructor(
    private val clientRepository: ClientRepository
) : GetAllClientsUseCase {
    override suspend fun invoke() = clientRepository.getAllClients()
}
