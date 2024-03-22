package com.lxy.songsearch.net

import com.lxy.songsearch.base.net.BaseNetFetchClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val iTunesApiService  = ITunesHttpNetFetchClient().creatApiService(ITunesApi::class.java, "https://itunes.apple.com/")


class ITunesHttpNetFetchClient : BaseNetFetchClient() {


    override fun configurationOkhttpClient(builder: OkHttpClient.Builder): OkHttpClient.Builder =
        builder.apply {

        }


    override fun configurationInterceptors(builder: OkHttpClient.Builder): OkHttpClient.Builder =
        builder.apply {
            val loginterceptor = HttpLoggingInterceptor()
            loginterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor())
        }


    override fun configurationRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder =
        builder.apply {
            addConverterFactory(GsonConverterFactory.create())
        }


}
