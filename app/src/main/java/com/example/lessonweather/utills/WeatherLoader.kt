package com.example.lessonweather.utills

import com.example.lessonweather.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val lat:Double,private val  lon:Double, private val onWeatherLoaded:OnWeatherLoaded) {

    fun loadWeather() {

        Thread {
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 3000
                addRequestProperty("X-Yandex-API-Key", "8a6d62b9-8c7c-4b42-a829-789b9767b393")
            }

            val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val weatherDTO : WeatherDTO? =
                Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
            onWeatherLoaded.onLoaded(weatherDTO)
        }.start()
    }
    private  fun convertBufferToResult(bufferedReader:BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed() // ДЗ
    }
}