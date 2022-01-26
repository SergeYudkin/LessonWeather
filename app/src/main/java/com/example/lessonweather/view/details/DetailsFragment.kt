package com.example.lessonweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresFeature
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lessonweather.BuildConfig
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.utills.*
import com.example.lessonweather.viewModel.AppState
import com.example.lessonweather.viewModel.DetailsViewModel
import com.example.lessonweather.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class DetailsFragment: Fragment() {




        private var _binding: FragmentDetailsBinding? = null
        private val binding : FragmentDetailsBinding
            get(){
                return _binding!!
            }

   /* private val receiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)?.let {
                    setWeatherData(it)
                }

            }
        }
    }*/



      private  val viewModel: DetailsViewModel by lazy {
            ViewModelProvider(this).get(DetailsViewModel::class.java)
        }


    private fun renderData(appState: AppState){
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    //HW
                }
                is AppState.Loading -> {
                    //HW
                }
                is AppState.Success -> {
                    val weather = appState.weatherData[0]
                    setWeatherData(weather)
                }
            }
        }

    }


        private lateinit var  localWeather: Weather
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel.getLiveData().observe(viewLifecycleOwner,{
                renderData(it)
            })
            arguments?.let {
                it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                    localWeather = it
                    viewModel.getWeatherFromRemoteServer(localWeather.city.lat,localWeather.city.lon)


                  /*  requireActivity().startService(Intent(requireActivity(),DetailsService::class.java).apply{  //  если  раскоментить этот код и код с коментом "регистрируем крик ресивера местный" то заработает
                        putExtra(BUNDLE_KEY_LAT,it.city.lat)
                        putExtra(BUNDLE_KEY_LON,it.city.lon)
                    })*/
                }
            }
           // requireActivity().registerReceiver(receiver, IntentFilter(BROADCAST_ACTION))  регистрируем общий крик ресивера
                //LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(BROADCAST_ACTION)) // регистрируем крик ресивера местный
        }

    private fun setWeatherData(weather: Weather) {
        with(binding) {
            with(localWeather) {
                    cityName.text = city.name
                    cityCoordinates.text = "${city.lat} ${city.lon}"
                    temperatureValue.text = "${weather.temperature}"
                    feelsLikeValue.text = "${weather.feelsLike}"

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding =null
        //  requireActivity().unregisterReceiver(receiver) // отключения общего
        //  LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver) // отключение местного
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentDetailsBinding.inflate(inflater,container,false)
            return binding.root
        }




        companion object {
            fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }



        }



}
