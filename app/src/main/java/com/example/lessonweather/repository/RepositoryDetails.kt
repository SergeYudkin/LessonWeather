package com.example.lessonweather.repository

import com.example.lessonweather.model.Weather
import okhttp3.Callback

interface RepositoryDetails {

    fun getWeatherFromServer( url:String, callback: Callback)


}