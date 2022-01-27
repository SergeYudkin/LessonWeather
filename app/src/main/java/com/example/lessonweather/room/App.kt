package com.example.lessonweather.room

import android.app.Application
import androidx.room.Room
import java.lang.IllegalStateException

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance:App? = null
         const val DB_NAME =  "History.db"
        private var db:HistoryDatabase? = null

        fun getHistoryWeatherDao(): HistoryWeatherDao {
            if (db == null) {
                if (appInstance == null) {
                    throw  IllegalStateException("Капец")
                }else {
                    db = Room.databaseBuilder(
                        appInstance!!.applicationContext,
                        HistoryDatabase::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()  // todo нужно убрать эту строку
                        .build()
                }

            }
            return db!!.historyWeatherDao()
        }

    }


}