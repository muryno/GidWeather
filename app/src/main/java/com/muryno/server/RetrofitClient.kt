package com.muryno.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{

        val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"


        private   var mInstance: RetrofitClient? = null

        @Synchronized
        fun getInstance(): RetrofitClient? {
            if (mInstance == null) {
                mInstance =
                    RetrofitClient()
            }
            return mInstance
        }
    }
    private var mRetrofit: Retrofit? = null

    init {


        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()

            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")

                    .build()
                chain.proceed(newRequest)

            }.addInterceptor(interceptor).build()


        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create())

            .build()
    }
    fun getApi(): ApiInterface? {
        return mRetrofit?.create(ApiInterface::class.java)
    }

    fun reset() {
        mInstance = null
        getInstance()
    }


}