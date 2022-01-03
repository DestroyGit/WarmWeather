package com.example.warmweather.view.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.warmweather.databinding.FragmentMainBinding
import com.example.warmweather.databinding.FragmentWeatherBinding
import com.example.warmweather.model.Weather
import com.example.warmweather.viewmodel.AppState
import com.example.warmweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

const val WEATHER_KEY = "WEATHER_KEY"

class WeatherFragment : Fragment() {

    var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
    get(){
        return _binding!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(WEATHER_KEY)
        if (weather!=null){
            Snackbar.make(
                    binding.root, "${weather.temperature}", Snackbar.LENGTH_LONG
                ).show()
                binding.resultWeather.text =
                    "${weather.city.name} ${weather.temperature}"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(bundle: Bundle):WeatherFragment{
            val fragment = WeatherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}