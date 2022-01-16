package com.example.warmweather.utils

import android.os.Handler
import android.os.Looper
import com.example.warmweather.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class WeatherLoader(private val onWeatherLoaded: OnWeatherLoaded) {
    fun loadWeather(lat: Double, lon: Double){
        val handlerMainUI = Handler(Looper.myLooper()!!)
        Thread{
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpURLConnection = (url.openConnection() as HttpURLConnection).apply{
                requestMethod = "GET" // тип запроса
                readTimeout = 2000
                addRequestProperty("X-Yandex-API-Key","84a54a9d-62d9-497c-b446-c28a4d96ef39")
            }

            // BufferedReader получает входящий поток InputStreamReader
            val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
            val weatherDTO: WeatherDTO? = Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)

            handlerMainUI.post(){
                onWeatherLoaded.onLoaded(weatherDTO)
//            httpURLConnection.disconnect()
            }
        }.start()
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n")) // получение исходного кода к странице, которую вызываем
    }

    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed()
    }
}