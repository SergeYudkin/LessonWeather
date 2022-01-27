package com.example.lessonweather.repository

import com.example.lessonweather.model.WeatherDTO

interface RepositoryDetails {

    fun getWeatherFromServer( lat:Double,lon:Double,  callback: retrofit2.Callback<WeatherDTO>)


}