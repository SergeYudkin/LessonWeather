package com.example.lessonweather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lessonweather.R
import com.example.lessonweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}