package com.example.lessonweather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO

const val BUNDLE_KEY = "key"
const val BUNDLE_KEY_WEATHER = "key_weather_dto"
const val BUNDLE_KEY_LAT = "key_lat"
const val BUNDLE_KEY_LON = "key_lon"
const val BROADCAST_ACTION = "BROADCAST_ACTION"

class DetailsFragment: Fragment() {




        private var _binding: FragmentDetailsBinding? = null
        private val binding : FragmentDetailsBinding
            get(){
                return _binding!!
            }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.getParcelableExtra<WeatherDTO>(BUNDLE_KEY_WEATHER)?.let {
                    setWeatherData(it)
                }

            }
        }
    }


        lateinit var  localWeather: Weather
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            arguments?.let {
                it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                    localWeather = it
                    requireActivity().startService(Intent(requireActivity(),DetailsService::class.java).apply{
                        putExtra(BUNDLE_KEY_LAT,it.city.lat)
                        putExtra(BUNDLE_KEY_LON,it.city.lon)
                    })
                }
            }
           // requireActivity().registerReceiver(receiver, IntentFilter(BROADCAST_ACTION))  регистрируем общий крик ресивера
                LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(BROADCAST_ACTION)) // регистрируем крик ресивера местный
        }

    private fun setWeatherData(weatherDTO: WeatherDTO) {
        with(binding) {
            with(localWeather) {
                    cityName.text = city.name
                    cityCoordinates.text = "${city.lat} ${city.lon}"
                    temperatureValue.text = "${weatherDTO.fact.temp}"
                    feelsLikeValue.text = "${weatherDTO.fact.feelsLike}"

            }
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentDetailsBinding.inflate(inflater,container,false)
            return binding.root
        }


        override fun onDestroy() {
            super.onDestroy()
            _binding =null
          //  requireActivity().unregisterReceiver(receiver) // отключения общего
            LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver) // отключение местного
        }

        companion object {
            fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }



        }



}
