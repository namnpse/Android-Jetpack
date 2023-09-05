package com.namnp.androidjetpack.di.manual_implement

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//equal (Java style)
/*
class NetworkModule {

    private static NetworkModule networkModule = null;

    public static NetworkModule getInstance(){
        synchronized (networkModule){
            if (networkModule == null){
                networkModule = new NetworkModule();
            }
        }
        return networkModule;
    }
}*/

//(Kotlin style)
object NetworkModule {
    private val gson: GsonConverterFactory by lazy { provideGson() }

    private fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

    private val headerInterceptor : HttpLoggingInterceptor by lazy {
        provideHeaderInterceptor(LibraryModule.application)
    }

    val okHttpClient : OkHttpClient by lazy {
        provideOkHttpClient(
            headerInterceptor = headerInterceptor
        )
    }

    private fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: GsonConverterFactory,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gson)
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }

    private fun provideOkHttpClient(
        headerInterceptor : HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .build()

    private fun provideHeaderInterceptor(
        context: Context,
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }
}
