package com.example.lessonweather.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lessonweather.R
import com.example.lessonweather.databinding.ActivityMainBinding
import com.example.lessonweather.model.MyBroadcastReceiver
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.room.App
import com.example.lessonweather.utills.BUNDLE_KEY
import com.example.lessonweather.utills.BUNDLE_KEY_WEATHER
import com.example.lessonweather.view.details.DetailsFragment
import com.example.lessonweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val receiver = MyBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)!=null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container,DetailsFragment.newInstance(
                    Bundle().apply {
                        putParcelable(BUNDLE_KEY,intent.getParcelableExtra<WeatherDTO>(
                            BUNDLE_KEY_WEATHER))
                    }
                )

                ).addToBackStack("").commit()
        }

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance())
                .commit()

        }
           val listWeather =  App.getHistoryWeatherDao().getAllHistoryWeather()

    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}