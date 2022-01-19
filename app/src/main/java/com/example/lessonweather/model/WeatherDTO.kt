package com.example.lessonweather.model


import com.google.gson.annotations.SerializedName

data class WeatherDTO(

    val fact: Fact,
)


data class Fact (

    val temp: Long,

    @SerializedName("feels_like")
    val feelsLike: Long,

    val icon: String,
    val condition: String,




    )