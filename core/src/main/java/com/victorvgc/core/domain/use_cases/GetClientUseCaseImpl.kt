package com.victorvgc.core.domain.use_cases

import com.victorvgc.core.domain.repositories.ClientRepository
import javax.inject.Inject

class GetClientUseCaseImpl @Inject constructor(
    private val clientRepository: ClientRepository
) : GetClientUseCase {
    override suspend fun invoke(clientName: String) = clientRepository.getClient(clientName)
}
