package com.milo.androidx.easier.retrofit

data class NetworkRes<B>(
    val body: B?,
    val error: Throwable?
)