package com.example.warmweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warmweather.model.RepositoryImpl
import java.lang.Thread.sleep

// 1. наследуем от ViewModel, который будет хранить LiveData, на которую будет подписываться Fragment
// Mutable - значит изменяемая LiveData, и даем ему значение MutableLiveData()
class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),

):ViewModel() {

    private val repositoryImpl: RepositoryImpl by lazy {
        RepositoryImpl()
    }

    // 2. посадили livedata
    fun getLiveData():LiveData<AppState>{
        return liveData
    } // 3. потом реализация ссылки во View(Fragment) для LiveData

    fun getWeatherFromLocalSourceRus() = getWeatherFromLocalServer(true)

    fun getWeatherFromLocalSourceWorld() = getWeatherFromLocalServer(false)

    fun getWeatherFromLocalSource() = getWeatherFromLocalServer(true)

    // 6. эмуляция изменения liveData/запрос погоды
    fun getWeatherFromLocalServer(isRussian: Boolean) {
        liveData.postValue(AppState.Loading(0))
        Thread{
            sleep(1500)

            liveData.postValue(AppState.Success(
                if (isRussian) repositoryImpl.getWeatherFromLocalStorageRus()
                else repositoryImpl.getWeatherFromLocalStorageWorld()
            ))
            // обновляем liveData через синхронное изменение postValue
            // liveData.value = Any() - асинхронное обновление
        }.start()
    }
}