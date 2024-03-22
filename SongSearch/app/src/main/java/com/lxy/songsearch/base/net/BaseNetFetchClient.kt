package com.lxy.songsearch.base.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit

abstract class BaseNetFetchClient {

    private val realClient: OkHttpClient
        get() {
            var builder = OkHttpClient.Builder()
            builder = configurationOkhttpClient(builder)
            builder = configurationInterceptors(builder)
            return builder.build()
        }


    fun <T> creatApiService(serviceClass: Class<T>, baseUrl: String): T {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(realClient)
        return configurationRetrofitBuilder(builder).build().create(serviceClass)
    }


    protected abstract fun configurationOkhttpClient(builder: OkHttpClient.Builder): OkHttpClient.Builder

    protected abstract fun configurationInterceptors(builder: OkHttpClient.Builder): OkHttpClient.Builder

    protected abstract fun configurationRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder


}