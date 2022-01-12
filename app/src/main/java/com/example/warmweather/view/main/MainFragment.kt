package com.example.warmweather.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.warmweather.R
import com.example.warmweather.databinding.FragmentMainBinding
import com.example.warmweather.model.Weather
import com.example.warmweather.view.details.WEATHER_KEY
import com.example.warmweather.view.details.WeatherFragment
import com.example.warmweather.viewmodel.AppState
import com.example.warmweather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(), OnMyItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() {
            return _binding!!
        }

    private val adapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(this)
    }
    private var isRussian = true

    private val viewModel: MainViewModel by lazy { // создание ссылки на ViewModel
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView() // вынесли binding из onViewCreated

        // 3. ViewModelProvider - хранилище всех ViewModel, следит, чтобы каждая ViewModel существовала в одном экземпляре
        // если вернуть несуществующий ViewModel, он ее создаст
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java) // перенесли выше в lazy

        // 4. подписываемся на обновления ViewModel LiveData через LifeCycleOwner
        // когда умирвет LifecycleOwner, он передает LifeData об этом, чтобы не было утечек памяти
        // observe - слушатель
        // Observer - колбэк, на который будут приходить ответы: будем рендерить (renderData) результат изменения LifeData
        // !! как только изменение, сразу результат
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppState> { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }

    @SuppressLint("SetTextI18n")
    fun renderData(appState: AppState) {
        with(binding) {
            when (appState) {
                // в случае Error показываем ошибку в виде Toast
                is AppState.Error -> {// Toast.makeText(requireContext(),appState.error.message, Toast.LENGTH_SHORT).show()
                    mainFragmentLoadingLayout.visibility = View.GONE
//                    Snackbar.make(root, "Error", Snackbar.LENGTH_LONG)
//                        .setAction("Try again") {
//                            sentRequest()
//                    }.show()
                    binding.root.showSnackBarWithAction("Error", "Try again", Snackbar.LENGTH_LONG)
                }
                // в случае Loading показываем прогресс загрузки
                is AppState.Loading -> //Toast.makeText(requireContext(),"${appState.progress}", Toast.LENGTH_SHORT).show()
                    mainFragmentLoadingLayout.visibility = View.VISIBLE
                // в случае успешного запуска, показываем погоду
                is AppState.Success -> {//Toast.makeText(requireContext(),"${appState.weatherData} ${appState.feelsLike}", Toast.LENGTH_SHORT).show()
                    mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherData)
                    binding.root.showSnackBarWithoutAction("Success", Snackbar.LENGTH_LONG)
//                Snackbar.make(
//                    binding.root, "${appState.weatherData.temperature}", Snackbar.LENGTH_LONG
//                ).show()
//                binding.resultWeather.text =
//                    "${appState.weatherData.city.name} ${appState.weatherData.temperature}"
                }
            }
            // 5. requireContext вместо Context, потмоу что тут есть проверка на null
//        Toast.makeText(requireContext(),"IT WORKS", Toast.LENGTH_SHORT).show()
        }
    }

    private fun View.showSnackBarWithoutAction(text: String, length: Int) {
        Snackbar.make(this, text, length).show()
    }

    private fun View.showSnackBarWithAction(title: String, text: String, length: Int) {
        Snackbar.make(this, title, length).setAction(text) {
            sentRequest()
        }.show()
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

    private fun sentRequest() {
        // меняем на противоположное значение
        with(binding) {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherFromLocalSourceRus()
                mainFragmentFAB.setImageResource(R.drawable.icon_russia)
            } else {
                viewModel.getWeatherFromLocalSourceWorld()
                mainFragmentFAB.setImageResource(R.drawable.icon_world)
            }
        }
    }

    override fun onItemClick(weather: Weather) {
        // создаем контейнер для передачи данных

        // Для чайников ниже, для продвинутых в newInstanceЖ: newInstance(Bundle().apply {putParcelable(WEATHER_KEY, weather))
//        val bundle = Bundle()
//        bundle.putParcelable(WEATHER_KEY, weather)

        activity?.run {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,
                    WeatherFragment.newInstance( // КАК СДЕЛАТЬ ТАК, ЧТОБЫ ADD ДОБАВЛЯЛ НЕПРОЗРАЧНЫЙ ФОН?
                        Bundle().apply {
                            putParcelable(WEATHER_KEY, weather)
                        }
                    )) //newInstance(bundle) - для чайников версия
                .addToBackStack("")
                .commit()
        }
    }

    private fun initView() {
        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentFAB.setOnClickListener() {
                sentRequest()
            }
        }
    }
}