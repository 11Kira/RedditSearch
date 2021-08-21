package com.test.redditsearch.core.modules

import com.test.redditsearch.core.ApiUtil
import com.test.redditsearch.core.CoreConstants
import com.test.redditsearch.core.response.ResponseHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { provideRetrofit(get()) }
    factory { provideOkHttpClient(get()) }
    factory { provideLoggingInterceptor() }
    factory { ResponseHandler() }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
    .Builder()
    .baseUrl(ApiUtil.getApiUrl())
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val client = OkHttpClient()
        .newBuilder()
        .connectTimeout(CoreConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(CoreConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(CoreConstants.READ_TIMEOUT, TimeUnit.SECONDS)
    client.addInterceptor(loggingInterceptor)
    return client.build()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logger = HttpLoggingInterceptor()
    logger.level = HttpLoggingInterceptor.Level.BODY
    return logger
}