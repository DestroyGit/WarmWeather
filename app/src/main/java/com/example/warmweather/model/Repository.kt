package com.example.warmweather.model

//репозиторий доступа к БД
interface Repository {
    fun getWeatherFromServer(): Weather // с сервера погоду возвращвет
    fun getWeatherFromLocalStorage(): Weather // локально возвращает погоду
}