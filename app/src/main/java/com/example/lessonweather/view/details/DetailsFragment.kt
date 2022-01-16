package com.example.lessonweather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather

const val BUNDLE_KEY = "key"

class DetailsFragment: Fragment() {




        private var _binding: FragmentDetailsBinding? = null
        private val binding : FragmentDetailsBinding
            get(){
                return _binding!!
            }




        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            arguments?.let {
                it.getParcelable<Weather>(BUNDLE_KEY)?.run {
                    setWeatherData(this)
                }
            }


        }

    private fun setWeatherData(weather: Weather) {
        with(binding){

            cityName.text = weather.city.name
            binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"
            binding.temperatureValue.text = "${weather.temperature}"
            binding.feelsLikeValue.text = "${weather.feelsLike}"
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
    }