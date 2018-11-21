package com.transports.mvvmtan.base

import android.arch.lifecycle.ViewModel
import com.transports.mvvmtan.injection.component.DaggerViewModelInjector
import com.transports.mvvmtan.injection.component.ViewModelInjector
import com.transports.mvvmtan.injection.module.NetworkModule
import com.transports.mvvmtan.ui.TanArret.TanArretListViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()
    init {
        inject()
    }
    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is TanArretListViewModel -> injector.inject(this)
        }
    }
}