package com.example.lessonweather.repository

import com.example.lessonweather.BuildConfig
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.model.getRussianCities
import com.example.lessonweather.model.getWorldCities
import com.example.lessonweather.utills.YANDEX_API_KEY
import com.example.lessonweather.utills.YANDEX_API_URL
import com.example.lessonweather.utills.YANDEX_API_URL_END_POINT
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryImpl: RepositoryCitiesList, RepositoryDetails {



    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
    override fun getWeatherFromServer(url: String, callback: Callback) {
        val builder = Request.Builder().apply {
            header(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            //url(YANDEX_API_URL + YANDEX_API_URL_END_POINT +"?lat=${localWeather.city.lat}&lon=${localWeather.city.lon}")
            url(url)
        }

        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }



       /* call?.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    response.body()?.let {
                        val json = it.string()
                        requireActivity().runOnUiThread{
                            setWeatherData(Gson().fromJson(json, WeatherDTO::class.java))
                        }

                    }

                }else{
                    //todo HW  1:13
                }
            }
        })
    }*/
}