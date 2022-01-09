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

        fun getWeatherFromLocalSourceRus()= getWeatherFromLocalServer(true)

        fun getWeatherFromLocalSourceWorld()= getWeatherFromLocalServer(false)

        fun getWeatherFromLocalSource()= getWeatherFromLocalServer(true) // заглушка на пятый урок




    fun getWeatherFromLocalServer(isRussian:Boolean){
        liveData.postValue(AppState.Loading(10))
        Thread{

            sleep(1000)
            liveData.postValue(AppState.Error(IllegalStateException("")))
            val rand = (1..40).random()
            if(true) {
                liveData.postValue(
                    AppState.Success(
                        if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus()
                        else repositoryImpl.getWeatherFromLocalStorageWorld()
                    )
                )

            }else{
               // liveData.postValue(AppState.Error(IllegalStateException("")))

            }

        }.start()
    }


}