package com.milo.androidx.easier.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseDataBindingFragment<T : ViewDataBinding> : Fragment() {

    private var hasInit = false

    protected lateinit var binding: T

    abstract fun fragmentLayoutId(): Int

    abstract fun initData(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!hasInit) {
            binding = DataBindingUtil.inflate(inflater, fragmentLayoutId(), container, false)
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!hasInit) {
            initData(savedInstanceState)
        }
        hasInit = true
    }
}