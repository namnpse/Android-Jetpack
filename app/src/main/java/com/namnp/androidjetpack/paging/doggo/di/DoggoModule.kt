package com.namnp.androidjetpack.paging.doggo.di

import android.content.Context
import com.namnp.androidjetpack.paging.doggo.data.repository.DoggoRepository
import com.namnp.androidjetpack.paging.doggo.data.repository.local.DoggoDatabase
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.DoggoApiService
import com.namnp.androidjetpack.paging.doggo.data.repository.remote.RemoteInjector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DoggoModule {

    @Singleton
    @Provides
    fun provideDoggoRepository(
        doggoApiService: DoggoApiService,
        appDatabase: DoggoDatabase,
    ): DoggoRepository {
        return DoggoRepository(doggoApiService, appDatabase)
    }

    // Remote
    @Singleton
    @Provides
    fun getDoggoApiService(retrofit: Retrofit): DoggoApiService {
        return retrofit.create(DoggoApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RemoteInjector.API_END_POINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun getOkHttpNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader(
                    RemoteInjector.HEADER_API_KEY,
                    RemoteInjector.API_KEY
                ).build()
            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun getOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor = getHttpLogger(),
        okHttpNetworkInterceptor: Interceptor = getOkHttpNetworkInterceptor()
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLogger)
            .addInterceptor(okHttpNetworkInterceptor)
            .build()
    }

    // Local
    @Singleton
    @Provides
    private fun initForDoggoFeature(@ApplicationContext context: Context): DoggoDatabase {
        return DoggoDatabase.getInstance(context)
    }
}