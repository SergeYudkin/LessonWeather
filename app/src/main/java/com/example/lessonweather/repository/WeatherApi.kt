package com.example.lessonweather.repository

import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.utills.YANDEX_API_KEY
import com.example.lessonweather.utills.YANDEX_API_URL_END_POINT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {

    @GET(YANDEX_API_URL_END_POINT)

    fun getWeather(@Header(YANDEX_API_KEY) apikey:String,
                   @Query("lat") lat:Double,
                   @Query("lon") lon:Double):Call<WeatherDTO>


}