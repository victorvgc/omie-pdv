package com.victorvgc.utils.extensions

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
