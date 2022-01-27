package com.example.lessonweather.viewModel

import com.example.lessonweather.model.Weather

sealed class AppState {

    data class Loading(val progress: Int): AppState()
    data class SuccessDetails(val  weatherData:List <Weather>):AppState()
    data class SuccessCity(val weatherData: List<Weather>):AppState()
    data class Error(val error: Int, val i: Int):AppState()

}