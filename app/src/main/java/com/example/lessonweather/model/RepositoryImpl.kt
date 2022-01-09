package com.example.lessonweather.model

class RepositoryImpl: Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus():Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageWorld(): Weather {
        return Weather()
    }
}