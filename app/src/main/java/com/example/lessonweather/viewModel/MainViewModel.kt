package com.example.lessonweather.viewModel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessonweather.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),
            private val repositoryImpl: RepositoryImpl = RepositoryImpl()): ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return liveData
    }

    fun getWeatherFromServer(){
        liveData.postValue(AppState.Loading(10))
        Thread{

            sleep(100)
            liveData.postValue(AppState.Error(IllegalStateException("")))
            val rand = (1..40).random()
            if(rand>10) {
                liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromServer()))
            }else{
                liveData.postValue(AppState.Error(IllegalStateException("")))

            }

        }.start()
    }

    fun getWeather(){
        // скоро будет переключатель
        getWeatherFromServer()
    }


}