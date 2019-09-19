package com.milo.androidx.easier.retrofit

import androidx.lifecycle.LiveData

interface Service {
    fun getData(): LiveData<NetworkRes<User>>
}