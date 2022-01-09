package com.example.lessonweather.model

interface Repository {

    fun getWeatherFromServer(): Weather
    fun getWeatherFromLocalStorageRus(): Weather
    fun getWeatherFromLocalStorageWorld(): Weather

}