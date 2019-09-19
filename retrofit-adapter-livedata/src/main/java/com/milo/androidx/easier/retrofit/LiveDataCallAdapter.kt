package com.milo.androidx.easier.retrofit

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class LiveDataCallAdapter<B>(
    private val responseType: Type
) : CallAdapter<B, LiveData<NetworkRes<B>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<B>): LiveData<NetworkRes<B>> {
        return NetworkLiveData(call)
    }

}