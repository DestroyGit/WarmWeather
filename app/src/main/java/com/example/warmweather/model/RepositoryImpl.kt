package com.example.warmweather.model

// класс, реализующий Repository
class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather = Weather()
    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()
    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()
}