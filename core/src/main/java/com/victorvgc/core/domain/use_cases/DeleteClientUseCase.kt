package com.victorvgc.core.domain.use_cases

import com.victorvgc.domain.core.Client
import com.victorvgc.utils.result_helper.ResultWrapper

interface DeleteClientUseCase {
    suspend operator fun invoke(client: Client): ResultWrapper<Unit>
}
