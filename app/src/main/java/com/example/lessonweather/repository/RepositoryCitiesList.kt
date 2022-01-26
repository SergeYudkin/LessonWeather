package com.example.lessonweather.repository

import com.example.lessonweather.model.Weather

interface RepositoryCitiesList {

    fun getWeatherFromLocalStorageRus(): List <Weather>
    fun getWeatherFromLocalStorageWorld(): List <Weather>

}