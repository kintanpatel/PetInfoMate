package com.manageairproducts.petinfomate.data.network


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimalRetrofit {

    private const val BASE_URL = "https://api.api-ninjas.com/v1/"
    private const val API_KEY = "tLWCLr4G06R5VEJtTpRUHwGkLCL7WTIVjG2QvyiZ"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Api-Key", API_KEY)
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    val api: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}