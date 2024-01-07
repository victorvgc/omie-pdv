package com.victorvgc.domain.utils

fun <T, Z> List<T>.reduceTo(reduceFunction: (acc: Z?, next: T) -> Z): Z? {
    if (this.isEmpty()) {
        return null
    }

    var result: Z? = null

    for (item in this) {
        result = reduceFunction(result, item)
    }

    return result
}
