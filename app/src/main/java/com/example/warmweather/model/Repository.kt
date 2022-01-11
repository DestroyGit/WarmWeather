package com.example.warmweather.model

//репозиторий доступа к БД
interface Repository {
    fun getWeatherFromServer(): Weather // с сервера погоду возвращвет
    fun getWeatherFromLocalStorageRus(): List<Weather> // локально возвращает погоду Рос. городом
    fun getWeatherFromLocalStorageWorld(): List<Weather> // локально возвращает погоду городов мира
}