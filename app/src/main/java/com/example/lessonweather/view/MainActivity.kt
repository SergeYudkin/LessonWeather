package com.example.lessonweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lessonweather.R
import com.example.lessonweather.databinding.ActivityMainBinding
import com.example.lessonweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment.newInstance())
                .commit()

        }


    }
}