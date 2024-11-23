package com.example.mealplanner.calender

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.R
import com.example.mealplanner.pojos.Meals

class CalenderAdapter(var data: List<WeekDays>, private var listener : CalenderListener) :
    RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder>() {

    private lateinit var context: Context

    class CalenderViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val dayName: ImageView = row.findViewById(R.id.dayName)
        val bfMeal: TextView = row.findViewById(R.id.bfMeal)
        val lMeal: TextView = row.findViewById(R.id.lunchMeal)
        val dMeal: TextView = row.findViewById(R.id.dinnerMeal)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.cal_item, parent, false)
        return CalenderViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalenderViewHolder, position: Int) {
        holder.dayName.setImageResource(data.get(position).img)

        holder.bfMeal.text = data.get(position).bfMeal
        if(!data.get(position).bfMeal.toString().isNullOrEmpty()){
            holder.bfMeal.setTextColor(Color.parseColor("#2413cd"))
            holder.bfMeal.setOnClickListener{
                listener.onTitleClick(data.get(position).bfMeal.toString())
            }
        }
       
        holder.lMeal.text = data.get(position).lMeal
        if (!data.get(position).lMeal.toString().isNullOrEmpty()) {
            holder.lMeal.setTextColor(Color.parseColor("#2413cd"))
            holder.lMeal.setOnClickListener {
                listener.onTitleClick(data.get(position).lMeal.toString())
            }
        }
        holder.dMeal.text = data.get(position).dMeal
        if (!data.get(position).dMeal.toString().isNullOrEmpty()) {
            holder.dMeal.setTextColor(Color.parseColor("#2413cd"))
            holder.dMeal.setOnClickListener {
                listener.onTitleClick(data.get(position).dMeal.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}