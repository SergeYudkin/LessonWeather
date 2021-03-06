package com.example.lessonweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lessonweather.R
import com.example.lessonweather.databinding.FragmentMainRecyclerCityItemBinding

import com.example.lessonweather.model.Weather

class MainFragmentAdapter(val listener: OnMyItemClickListener): RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {


    private var weatherData:List<Weather> = listOf()

    fun setWeather(data:List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapter.MainViewHolder {
        val binding: FragmentMainRecyclerCityItemBinding = FragmentMainRecyclerCityItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
            return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

   inner class MainViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(weather: Weather){
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.name
            itemView.setOnClickListener {
                listener.onItemClick(weather)
            }
        }
    }


}