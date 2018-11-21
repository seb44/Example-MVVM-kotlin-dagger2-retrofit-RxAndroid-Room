package com.transports.mvvmtan.network

import com.transports.mvvmtan.model.TanArret
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface TanApi {

    @GET("/arrets")
    fun getTanStops(): Observable<List<TanArret>>

    @GET("/arrets")
    fun getTanArrets(): Single<List<TanArret>>
}