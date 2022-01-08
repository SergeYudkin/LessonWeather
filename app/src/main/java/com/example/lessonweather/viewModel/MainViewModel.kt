package com.example.lessonweather.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(private val liveData: MutableLiveData<Any> = MutableLiveData()) : ViewModel() {

    fun getLiveData(): LiveData<Any>{
        return liveData
    }
}