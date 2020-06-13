package com.route.islami.ApiManager

import com.route.islami.ApiManager.model.RadioResponse
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {

    @GET("radios/radio_arabic.json")
    fun getRadioChannels(): Call<RadioResponse>
}