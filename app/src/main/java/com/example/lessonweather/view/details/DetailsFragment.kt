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
import com.example.lessonweather.BuildConfig
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.utills.*
import okhttp3.OkHttpClient
import okhttp3.Request


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

    private var client : OkHttpClient? = null

    fun getWeather(): OkHttpClient? {

        val builder = Request.Builder()
        builder.header(YANDEX_API_KEY,BuildConfig.WEATHER_API_KEY)
        builder.url(YANDEX_API_URL+ YANDEX_API_URL_END_POINT+
                "lat = ${localWeather.city.lat} lon = ${localWeather.city.lon}")

        return okHttpClient()

    }

    private fun DetailsFragment.okHttpClient(): OkHttpClient? {
        if (client == null) {
            client = OkHttpClient()

        }
        return client
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
