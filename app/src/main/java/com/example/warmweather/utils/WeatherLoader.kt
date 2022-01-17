package com.example.warmweather.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.warmweather.BuildConfig
import com.example.warmweather.model.WeatherDTO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

class WeatherLoader(private val onWeatherLoaded: OnWeatherLoaded) {

    fun loadWeather(lat: Double, lon: Double){
        val handlerMainUI = Handler(Looper.myLooper()!!)
        val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
        val httpURLConnection = url.openConnection() as HttpURLConnection
        Thread{
            try {
                httpURLConnection.apply {
                    requestMethod = "GET" // тип запроса
                    readTimeout = 2000
                    addRequestProperty(
                        "X-Yandex-API-Key",
                        BuildConfig.WEATHER_API_KEY
                    )
                }

                // BufferedReader получает входящий поток InputStreamReader
                val bufferedReader =
                    BufferedReader(InputStreamReader(httpURLConnection.inputStream))
                val weatherDTO: WeatherDTO? = Gson().fromJson(
                    convertBufferToResult(bufferedReader),
                    WeatherDTO::class.java
                )
                handlerMainUI.post{
                    onWeatherLoaded.onLoaded(weatherDTO)
                }
            } catch (e: Exception){
                Log.d("MISTAKE", "catch")
                handlerMainUI.post(){
                    onWeatherLoaded.onFailed(e)
                }
            } finally {
                httpURLConnection.disconnect()
            }
        }.start()
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader):String{
        return bufferedReader.lines().collect(Collectors.joining("\n")) // получение исходного кода к странице, которую вызываем
    }

    interface OnWeatherLoaded{
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed(e: Exception)
    }
}