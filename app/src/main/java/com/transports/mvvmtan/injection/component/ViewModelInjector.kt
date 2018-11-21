package com.transports.mvvmtan.injection.component

import com.transports.mvvmtan.injection.module.NetworkModule
import com.transports.mvvmtan.ui.TanArret.TanArretListViewModel
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified tanArretListViewModel.
     * @param TanArretListViewModel TanArretListViewModel in which to inject the dependencies
     */
    fun inject(tanArretListViewModel: TanArretListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}