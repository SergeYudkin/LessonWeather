package com.example.lessonweather.utills

import android.os.Handler
import android.os.Looper
import com.example.lessonweather.BuildConfig
import com.example.lessonweather.model.WeatherDTO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(private val onWeatherLoaded:OnWeatherLoaded) {

    fun loadWeather(lat:Double,lon:Double) {

        Thread {
            try {
                val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
                val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 3000
                    addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
                }

                val bufferedReader =
                    BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO? =
                    Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
                Handler(Looper.getMainLooper()).post {
                    onWeatherLoaded.onLoaded(weatherDTO)
                }

            }catch (e:Throwable){
                onWeatherLoaded.onFailed("Error",Snackbar.LENGTH_LONG)
            }


        }.start()
    }
    private  fun convertBufferToResult(bufferedReader:BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)

        fun onFailed(text:String, length:Int){

        }

    }
}