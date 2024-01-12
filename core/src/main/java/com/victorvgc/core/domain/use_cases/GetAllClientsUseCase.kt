package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Client
import com.victorvgc.utils.result_helper.ResultWrapper

interface GetAllClientsUseCase {
    suspend operator fun invoke(): ResultWrapper<List<Client>>
}
