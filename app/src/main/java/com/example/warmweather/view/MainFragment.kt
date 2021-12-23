package com.example.warmweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warmweather.R
import com.example.warmweather.databinding.ActivityMainBinding
import com.example.warmweather.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
    get(){
        return _binding!!
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