package com.example.warmweather.view.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmweather.databinding.FragmentWeatherBinding
import com.example.warmweather.model.Weather
import com.google.android.material.snackbar.Snackbar

const val WEATHER_KEY = "WEATHER_KEY"

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() {
            return _binding!!
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // реализация для чайников:
//        val weather = arguments?.getParcelable<Weather>(WEATHER_KEY)
//        if (weather!=null){
//            setWeatherData(weather)
//        }
        // ниже реализация через let/run
        arguments?.let {
            it.getParcelable<Weather>(WEATHER_KEY)?.run {
                setWeatherData(this)
            }
        }
    }

    private fun setWeatherData(weather: Weather) {
        with(binding) {
            binding.root.showSnackBar("${weather.temperature}", Snackbar.LENGTH_LONG)
//            Snackbar.make(
//                root, "${weather.temperature}", Snackbar.LENGTH_LONG
//            ).show()
            resultWeather.text =
                "${weather.city.name} ${weather.temperature}"
        }
    }

    private fun View.showSnackBar(text: String, length: Int) {
        Snackbar.make(this, text, length).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        // Запись для чайников:
//            fun newInstance(bundle: Bundle):WeatherFragment{
//            val fragment = WeatherFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
        // Это же через apply:
        fun newInstance(bundle: Bundle) = WeatherFragment().apply { arguments = bundle }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}