package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Client
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetClientUseCase {

    suspend operator fun invoke(clientName: String): ResultWrapper<Client>
}
