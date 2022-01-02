package com.example.warmweather.viewmodel

import com.example.warmweather.model.Weather

// 7. sealed запечатанный класс (подставили вместо Any в процессе работы - везде, где до (6) есть AppState, было Any())
sealed class AppState {
    // 8. если не передае   м параметров, то будет: object Success: AppState(). если с параметрами, то:
    data class Loading(val progress: Int): AppState() //здесь показываем состояние загрузки
    data class Success(val weatherData:List<Weather>): AppState() //здесь будем хранить результат сервера - погодные данные
    data class Error(val error: Throwable): AppState() //здесь храним код ошибки
}