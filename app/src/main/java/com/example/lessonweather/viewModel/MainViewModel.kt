package com.example.lessonweather.viewModel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lessonweather.R
import com.example.lessonweather.repository.RepositoryImpl
import java.lang.Thread.sleep

class MainViewModel(private val liveData: MutableLiveData<AppState> = MutableLiveData(),

): ViewModel() {
    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }



    fun getLiveData() = liveData



        fun getWeatherFromLocalSourceRus()= getWeatherFromLocalServer(true)

        fun getWeatherFromLocalSourceWorld()= getWeatherFromLocalServer(false)





    private fun getWeatherFromLocalServer(isRussian:Boolean){
        with(liveData){
            postValue(AppState.Loading(100))
            Thread{
                sleep(1000)
                postValue(AppState.Error(R.string.serverError, 418))

                postValue(
                    AppState.SuccessDetails(
                        with(repositoryImpl){
                            if (isRussian) getWeatherFromLocalStorageRus()
                            else getWeatherFromLocalStorageWorld()
                        }

                    )
                )

            }.start()
        }

    }

}