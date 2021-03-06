package com.example.warmweather.view.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.warmweather.databinding.FragmentWeatherBinding
import com.example.warmweather.model.Weather
import com.example.warmweather.model.WeatherDTO
import com.example.warmweather.utils.WeatherLoader
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

const val WEATHER_KEY = "WEATHER_KEY"

class WeatherFragment : Fragment(), WeatherLoader.OnWeatherLoaded {

    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding
        get() {
            return _binding!!
        }

    lateinit var localWeather: Weather

    private val weatherLoader = WeatherLoader(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // реализация для чайников:
//        val weather = arguments?.getParcelable<Weather>(WEATHER_KEY)
//        if (weather!=null){
//            setWeatherData(weather)
//        }
        // ниже реализация через let/run
        arguments?.let { it ->
            it.getParcelable<Weather>(WEATHER_KEY)?.let {
                localWeather = it
                weatherLoader.loadWeather(it.city.lat, it.city.lon)
            }
        }
    }

    private fun setWeatherData(weatherDTO: WeatherDTO) {
        with(localWeather){
            binding.cityName.text = city.name
        }

        with(binding) {
            temperature.text = "${weatherDTO.fact.temp}"
            feelsLike.text = "${weatherDTO.fact.feelsLike}"
            root.showSnackBar("${weatherDTO.fact.temp}", Snackbar.LENGTH_LONG)

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

    override fun onLoaded(weatherDTO: WeatherDTO?) {
        weatherDTO?.let {
            setWeatherData(weatherDTO)
        }
    }

    override fun onFailed(e: Exception) {
//        binding.root.showSnackBar("fail",Snackbar.LENGTH_SHORT)
        Log.d("MISTAKE", "${e.message}")
        Toast.makeText(requireActivity(),"ERROR: ${e.message}",Toast.LENGTH_SHORT).show()
    }
}