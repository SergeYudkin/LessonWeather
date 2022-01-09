package com.example.lessonweather.viewModel

import com.example.lessonweather.model.Weather

sealed class AppState {

    data class Loading(val process: Int): AppState()
    data class Success(val  weatherData:List <Weather>):AppState()
    data class Error (val error: Throwable):AppState()

}