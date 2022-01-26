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
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.io.IOException

class RepositoryImpl: RepositoryCitiesList, RepositoryDetails {



    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

   private val retrofit =  Retrofit.Builder().baseUrl(YANDEX_API_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient()
            .create()))
        .build().create(WeatherApi::class.java)


    override fun getWeatherFromServer(lat:Double,lon:Double, callback: Callback<WeatherDTO>) {


        retrofit.getWeather(BuildConfig.WEATHER_API_KEY ,lat,lon).enqueue(callback)
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