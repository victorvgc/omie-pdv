package com.victorvgc.domain.core

data class Client(
    val name: String
) {
    companion object {
        val Empty = Client("")
    }
}
