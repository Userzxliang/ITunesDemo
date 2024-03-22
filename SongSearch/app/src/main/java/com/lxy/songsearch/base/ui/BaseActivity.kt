package com.lxy.songsearch.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : FragmentActivity() {
    protected lateinit var binding: VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)
        onCreateUI(savedInstanceState)
        onCreatObserver()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    abstract fun onCreateUI(savedInstanceState: Bundle?)

    abstract fun onCreatObserver()

    abstract fun getViewBinding(layoutInflater: LayoutInflater): VB

    open fun loadData() {}

}