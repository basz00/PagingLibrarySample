package com.baz.paging

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

internal class RestApiFactory {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create() = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(providesOkHttpClient())
            .build()

        private fun providesOkHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(1, TimeUnit.MINUTES)
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.writeTimeout(1, TimeUnit.MINUTES)
            httpClient.addInterceptor(loggingInterceptor())
            httpClient.addInterceptor {
                val requestOriginal = it.request()
                val httpUrl = requestOriginal.url().newBuilder()
                    .addQueryParameter("api_key", "2846728077248553eda70aff745e453f")
                    .build()
                val request = requestOriginal.newBuilder()
                    .url(httpUrl)
                    .build()
                it.proceed(request)
            }
            return httpClient.build()
        }

        private fun loggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            return interceptor
        }
    }
}