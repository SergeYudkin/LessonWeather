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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lessonweather.BuildConfig
import com.example.lessonweather.databinding.FragmentDetailsBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.model.WeatherDTO
import com.example.lessonweather.utills.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


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

    private fun getWeather(){
       if (client==null)
           client = OkHttpClient()

        val builder = Request.Builder().apply {
            header(YANDEX_API_KEY,BuildConfig.WEATHER_API_KEY)
            url(YANDEX_API_URL+ YANDEX_API_URL_END_POINT+"?lat=${localWeather.city.lat}" +
                    "&lon=${localWeather.city.lon}")
        }

       val request =  builder.build()
       val call =  client?.newCall(request)


        call?.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    response.body()?.let {
                        val json = it.string()
                        requireActivity().runOnUiThread{
                            setWeatherData(Gson().fromJson(json, WeatherDTO::class.java))
                        }

                    }

                }else{
                    //todo hw  1:13
                }
            }
        })
    }


        private lateinit var  localWeather: Weather
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            arguments?.let {
                it.getParcelable<Weather>(BUNDLE_KEY)?.let {
                    localWeather = it
                    getWeather()
                    /*requireActivity().startService(Intent(requireActivity(),DetailsService::class.java).apply{  //  если  раскоментить этот код и код с коментом "регистрируем крик ресивера местный" то заработает
                        putExtra(BUNDLE_KEY_LAT,it.city.lat)
                        putExtra(BUNDLE_KEY_LON,it.city.lon)
                    })*/
                }
            }
           // requireActivity().registerReceiver(receiver, IntentFilter(BROADCAST_ACTION))  регистрируем общий крик ресивера
               // LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(BROADCAST_ACTION)) // регистрируем крик ресивера местный
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
           // LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver) // отключение местного
        }

        companion object {
            fun newInstance(bundle: Bundle) = DetailsFragment().apply { arguments = bundle }



        }



}
