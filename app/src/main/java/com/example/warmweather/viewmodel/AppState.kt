package com.example.warmweather.viewmodel

// 7. sealed запечатанный класс (подставили вместо Any в процессе работы - везде, где до (6) есть AppState, было Any())
sealed class AppState {
    // 8. если не передаем параметров, то будет: object Success: AppState(). если с параметрами, то:
    data class Loading(var progress: Int): AppState() //здесь показываем состояние загрузки
    data class Success(var weatherData: String, var feelsLike: Int): AppState() //здесь будем хранить результат сервера - погодные данные
    data class Error(var error: Throwable): AppState() //здесь храним код ошибки
}