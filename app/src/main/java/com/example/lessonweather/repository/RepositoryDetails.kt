package com.example.lessonweather.repository

import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import okhttp3.Callback

interface RepositoryDetails {

    fun getWeatherFromServer( lat:Double,lon:Double,  callback: retrofit2.Callback<WeatherDTO>)


}