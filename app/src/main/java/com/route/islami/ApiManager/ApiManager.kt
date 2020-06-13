package com.route.islami.ApiManager

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {
        private var retrofit: Retrofit? = null;

        private fun getInstance(): Retrofit {
            val httpInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("api", message);
                }
            })
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpInterceptor)
                .build();


            if (retrofit == null) {
                //initialize
                retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://api.mp3quran.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }

            return retrofit ?: Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.mp3quran.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

        fun getWebServices(): WebServices {
            return getInstance().create(WebServices::class.java)
        }
    }
}