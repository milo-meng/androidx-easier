package com.milo.androidx.easier.retrofit

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.atomic.AtomicBoolean

internal class NetworkLiveData<B, R : NetworkRes<B>>(private val call: Call<B>) : LiveData<R>() {

    private val started = AtomicBoolean(false)

    override fun onActive() {
        super.onActive()
        if (started.compareAndSet(false, true)) {
            call.enqueue(object : Callback<B> {
                override fun onResponse(call: Call<B>, response: Response<B>) {
                    handleResponse(response)
                }

                override fun onFailure(call: Call<B>, throwable: Throwable) {
                    handleError(throwable)
                }
            })
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (call.isExecuted) call.cancel()
    }

    private fun handleResponse(response: Response<B>) {
        val res = if (response.isSuccessful) {
            if (response.body() != null) {
                NetworkRes(response.body(), null)
            } else {
                NetworkRes(null, Throwable("empty response"))
            }
        } else {
            val msg = response.errorBody()?.string()
            val errorMsg = if (msg.isNullOrEmpty()) {
                response.message()
            } else {
                msg
            }
            NetworkRes(null, Throwable(errorMsg ?: "unknown error"))
        }
        @Suppress("UNCHECKED_CAST")
        postValue(res as R)
    }

    private fun handleError(cause: Throwable) {
        val res = NetworkRes(null, Throwable(cause.message ?: "unknown error", cause))
        @Suppress("UNCHECKED_CAST")
        postValue(res as R)
    }

}