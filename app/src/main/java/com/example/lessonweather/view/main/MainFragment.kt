package com.example.lessonweather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lessonweather.R
import com.example.lessonweather.databinding.FragmentMainBinding
import com.example.lessonweather.model.Weather
import com.example.lessonweather.utills.BUNDLE_KEY
import com.example.lessonweather.view.details.DetailsFragment
import com.example.lessonweather.viewModel.AppState
import com.example.lessonweather.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment(), OnMyItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding : FragmentMainBinding
    get(){
        return _binding!!
    }

    private val adapter : MainFragmentAdapter by lazy{
        MainFragmentAdapter(this)
    }
    private var isRussian = true

    private  val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) }) //внимание
        viewModel.getWeatherFromLocalSourceRus()
    }

    private fun initView() {
        with(binding){mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentFAB.setOnClickListener {
            sentRequest()
        }
        }
    }

    private fun sentRequest() {
        isRussian = !isRussian
        with(viewModel){
            with(binding){
                if (isRussian) {
                    getWeatherFromLocalSourceRus()
                    mainFragmentFAB.setImageResource(R.drawable.ic_russia)
                } else {
                    getWeatherFromLocalSourceWorld()
                    mainFragmentFAB.setImageResource(R.drawable.ic_earth)
                }
            }
        }

    }

    private fun renderData(appState:AppState){
        with(binding) {
            when (appState) {
                is AppState.Error -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                    root.actionErr("Error",Snackbar.LENGTH_LONG)

                }
                is AppState.Loading -> {
                    mainFragmentLoadingLayout.visibility = View.GONE
                }
                is AppState.Success -> {
                    mainFragmentLoadingLayout.visibility = View.GONE

                    adapter.setWeather(appState.weatherData)

                   root.showSnackBarWithoutAction("Success",Snackbar.LENGTH_LONG)
                }
            }
        }

     }

    private fun View.actionErr(text:String,length:Int){
        Snackbar.make(this,text ,length )
            .setAction("Попробовать ещё раз") {
                sentRequest()
            }.show()
    }


   private fun View.showSnackBarWithoutAction(text:String,length:Int){
        Snackbar.make(this,text,length).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {

        fun newInstance() = MainFragment()

    }

    override fun onItemClick(weather: Weather) {

        activity?.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.container,
                    DetailsFragment.newInstance(
                        Bundle().apply {
                            putParcelable(BUNDLE_KEY, weather)
                        }
                    ))
                .addToBackStack("").commit()

        }
    }
}