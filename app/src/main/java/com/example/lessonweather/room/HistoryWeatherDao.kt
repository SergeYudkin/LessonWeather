package com.example.lessonweather.room

import androidx.room.*

@Dao
interface HistoryWeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun  insert(entity: HistoryWeatherEntity)


    @Delete
    fun  delete (entity: HistoryWeatherEntity)

    @Update
    fun  update (entity: HistoryWeatherEntity)


    @Query("select * FROM history_weather_entity")
    fun getAllHistoryWeather():List<HistoryWeatherEntity>

    //fun getAllHistoryWeather()  // todo получить по какому то полю хотелось бы



}