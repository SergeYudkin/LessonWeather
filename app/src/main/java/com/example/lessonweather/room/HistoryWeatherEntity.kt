package com.example.lessonweather.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_weather_entity")
data class HistoryWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id:Long, val temperature:Int, val  feelsLike:Int, val icon:String
)
