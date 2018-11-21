package com.transports.mvvmtan.injection.module

import com.transports.mvvmtan.network.TanApi
import com.transports.mvvmtan.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.Reusable

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {
    /**
     * Provides the Tan service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Tan service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideTanApi(retrofit: Retrofit): TanApi {
        return retrofit.create(TanApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}