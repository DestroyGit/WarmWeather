package com.example.warmweather.model

// API яндекса

// параметрам задаем значения, чтобы в RepositoryImpl срабатывало по умолчанию
data class Weather(val city: City = getDefaultCity(), val temperature: Int = -20, val feelsLike: Int = -25)

data class City(val name: String, val lat: Double, val lon: Double)

fun getDefaultCity() = City("Moscow",55.33, 37.22)