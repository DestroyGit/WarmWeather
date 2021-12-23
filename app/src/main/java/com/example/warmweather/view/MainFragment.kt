package com.example.warmweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.warmweather.R
import com.example.warmweather.databinding.ActivityMainBinding
import com.example.warmweather.databinding.FragmentMainBinding
import com.example.warmweather.viewmodel.AppState
import com.example.warmweather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
    get(){
        return _binding!!
    }

    private lateinit var viewModel: MainViewModel // создание ссылки на ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3. ViewModelProvider - хранилище всех ViewModel, следит, чтобы каждая ViewModel существовала в одном экземпляре
        // если вернуть несуществующий ViewModel, он ее создаст
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // 4. подписываемся на обновления ViewModel LiveData через LifeCycleOwner
        // когда умирвет LifecycleOwner, он передает LifeData об этом, чтобы не было утечек памяти
        // observe - слушатель
        // Observer - колбэк, на который будут приходить ответы: будем рендерить (randerData) результат изменения LifeData
        // !! как только изменение, сразу результат
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        // 6. запускаем эмуляцию/запрашиваем погоду
        viewModel.getWeatherFromServer()
    }

    fun renderData(appState: AppState){
        when(appState){
            // в случае Error показываем ошибку в виде Toast
            is AppState.Error -> Toast.makeText(requireContext(),appState.error.message, Toast.LENGTH_SHORT).show()
            // в случае Loading показываем прогресс загрузки
            is AppState.Loading -> Toast.makeText(requireContext(),"${appState.progress}", Toast.LENGTH_SHORT).show()
            // в случае успешного запуска, показываем погоду
            is AppState.Success -> Toast.makeText(requireContext(),"${appState.weatherData} ${appState.feelsLike}", Toast.LENGTH_SHORT).show()

        }
        // 5. requireContext вместо Context, потмоу что тут есть проверка на null
//        Toast.makeText(requireContext(),"IT WORKS", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}