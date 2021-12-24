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
    private val repositoryImpl: RepositoryImpl = RepositoryImpl()
):ViewModel() {

    // 2. посадили livedata
    fun getLiveData():LiveData<AppState>{
        return liveData
    } // 3. потом реализация ссылки во View(Fragment) для LiveData

    // 6. эмуляция изменения liveData/запрос погоды
    fun getWeatherFromServer() {
        liveData.postValue(AppState.Loading(0))
        Thread{
            sleep(1500)
            liveData.postValue(AppState.Success(repositoryImpl.getWeatherFromServer()))
            // обновляем liveData через синхронное изменение postValue
            // liveData.value = Any() - асинхронное обновление
        }.start()
    }


}