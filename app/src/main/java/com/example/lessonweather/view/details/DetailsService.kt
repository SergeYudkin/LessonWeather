package com.example.lessonweather.view.details

import android.app.IntentService
import android.content.Intent
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

const val DETAILS_SERVICE_KEY_EXTRAS = "key"


class DetailsService (name: String=""):IntentService(name){



    override fun onHandleIntent(intent: Intent?) {
        loadWeather(lat, lon)
    }

    fun loadWeather() {


            try {
                val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
                val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                    requestMethod = "GET"
                    readTimeout = 3000
                    addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                }

                val bufferedReader =
                    BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
                val weatherDTO: WeatherDTO? =
                    Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
                    // не куда отправить знвчения


            }catch (e : Throwable){
                onWeatherLoaded.onFailed("Error", Snackbar.LENGTH_LONG)
            }
            finally {
                // httpsURLConnection.disconnect()
            }



    }
    private  fun convertBufferToResult(bufferedReader: BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}