package com.milo.androidx.easier.databinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseAppCompatDataBindingActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    abstract fun activityLayoutId(): Int

    abstract fun initData(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, activityLayoutId())
        binding.lifecycleOwner = this
        initData(savedInstanceState)
    }
}