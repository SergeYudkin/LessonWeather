package com.example.lessonweather.view.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.utills.WeatherLoader

const val BUNDLE_KEY = "key"

class DetailsFragment: Fragment(), WeatherLoader.OnWeatherLoaded {




        private var _binding: FragmentDetailsBinding? = null
        private val binding : FragmentDetailsBinding
            get(){
                return _binding!!
            }



        lateinit var  localWeather: Weather
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            arguments?.let {
                it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                    localWeather = it
                    WeatherLoader(it.city.lat,it.city.lon,this).loadWeather()
                }
            }


        }

    private fun setWeatherData(weatherDTO: WeatherDTO) {
        with(binding) {
            with(localWeather) {
                requireActivity().runOnUiThread {
                    cityName.text = city.name
                    cityCoordinates.text = "${city.lat} ${city.lon}"
                    temperatureValue.text = "${weatherDTO.fact.temp}"
                    feelsLikeValue.text = "${weatherDTO.fact.feelsLike}"
                }
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
        }

        companion object {
            fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }



        }

    override fun onLoaded(weatherDTO: WeatherDTO?) {

        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
        Log.d("","")
    }

    override fun onFailed() {
        //ДЗ
    }
}
