package com.example.lessonweather.viewModel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.model.getDefaultCity
import com.example.lessonweather.repository.RepositoryImpl
import com.example.lessonweather.utills.YANDEX_API_URL
import com.example.lessonweather.utills.YANDEX_API_URL_END_POINT
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.IOException
import java.lang.Thread.sleep

class DetailsViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),

): ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    fun getLiveData() = liveData






    fun getWeatherFromRemoteServer(lat:Double,lon:Double){
        liveData.postValue(AppState.Loading(0))
            repositoryImpl.getWeatherFromServer(lat,lon,callback)

    }

    fun converterDTOtoModel(weatherDTO: WeatherDTO): List<Weather>{
        return listOf(Weather(getDefaultCity(),weatherDTO.fact.temp.toInt(), weatherDTO.fact.feelsLike.toInt(),weatherDTO.fact.icon))
    }

     private val callback = object : Callback<WeatherDTO> {


           override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
               if (response.isSuccessful){
                   response.body()?.let {
                       liveData.postValue(AppState.Success(converterDTOtoModel(it)))

                       }

                   } else{
                   //todo HW  1:13
               }
           }

         override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
             // HW TODO("Not yet implemented")
         }
     }

}