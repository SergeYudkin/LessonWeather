package com.example.lessonweather.viewModel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessonweather.R
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.model.getDefaultCity
import com.example.lessonweather.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

  /*  fun converterDTOtoModel(weatherDTO: WeatherDTO): List<Weather>{
        return listOf(Weather(getDefaultCity(),weatherDTO.fact.temp.toInt(), weatherDTO.fact.feelsLike.toInt(),weatherDTO.fact.icon))
    }*/

     private val callback = object : Callback<WeatherDTO> {


           override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
               if (response.isSuccessful){
                   response.body()?.let {
                       liveData.postValue(AppState.SuccessCity(listOf(Weather(getDefaultCity(),
                           it.fact.temp.toInt(), it.fact.feelsLike.toInt(),it.fact.icon))))

                       }

                   } else{
                   liveData.postValue(AppState.Error(R.string.errorCode,response.code()))
               }
           }



         override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
             liveData.postValue(AppState.Error(R.string.serverError,418))
         }
     }

}