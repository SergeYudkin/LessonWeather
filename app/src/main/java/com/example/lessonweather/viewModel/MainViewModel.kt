package com.example.lessonweather.viewModel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessonweather.model.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),
                    private val repositoryImpl: RepositoryImpl = RepositoryImpl()): ViewModel() {

    fun getLiveData() = liveData



        fun getWeatherFromLocalSourceRus()= getWeatherFromLocalServer(true)

        fun getWeatherFromLocalSourceWorld()= getWeatherFromLocalServer(false)

        fun getWeatherFromLocalSource()= getWeatherFromLocalServer(true) // заглушка на пятый урок




    fun getWeatherFromLocalServer(isRussian:Boolean){
        liveData.postValue(AppState.Loading(10))
        Thread{

            sleep(100)
            liveData.postValue(AppState.Error(IllegalStateException("")))

                liveData.postValue(
                    AppState.Success(
                        with(repositoryImpl){
                            if (isRussian) getWeatherFromLocalStorageRus()
                            else getWeatherFromLocalStorageWorld()
                        }

                    )
                )


        }.start()
    }


}