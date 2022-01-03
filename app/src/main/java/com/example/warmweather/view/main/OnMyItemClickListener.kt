package com.example.warmweather.view.main

import com.example.warmweather.model.Weather

//Слушатель нажатия по городам для вызова WeatherFragment
interface OnMyItemClickListener {

    fun onItemClick(weather: Weather)
}