package com.victorvgc.utils.failures

enum class FailureCodes(val code: Int) {
    FAILURE_CODE_GENERIC(-1),
    FAILURE_CODE_CREATE(1),
    FAILURE_CODE_READ(2),
    FAILURE_CODE_UPDATE(3),
    FAILURE_CODE_DELETE(4),
    FAILURE_CODE_READ_ALL(5),
}
