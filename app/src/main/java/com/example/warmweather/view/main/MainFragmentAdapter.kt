package com.example.warmweather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.warmweather.R
import com.example.warmweather.model.Weather

class MainFragmentAdapter: RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var weatherData: List<Weather> = listOf() //пока просто пустой лист, заглушка

    fun setWeather(data:List<Weather>){
        this.weatherData = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapter.MainViewHolder{
        // возвращаем в скобках надутый main_recycler_item
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        holder.bind(this.weatherData[position])
    }

    override fun getItemCount(): Int {
        return weatherData.size
    }

    // переопределяем вьюхолдер
    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){

        //будет вызываться в onBindViewHolder
        fun bind(weather: Weather){
            // по умолчанию мы имеем itemView в main_recycler_item
            itemView.findViewById<TextView>(R.id.mainFragmentRecyclerItemTextView).text = weather.city.name
            itemView.setOnClickListener(){
                Toast.makeText(itemView.context, "It works! ${weather.city.name}",Toast.LENGTH_SHORT).show()
            }
        }
    }
}