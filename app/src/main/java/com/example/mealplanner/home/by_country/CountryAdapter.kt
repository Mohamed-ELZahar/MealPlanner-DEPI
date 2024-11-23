package com.example.mealplanner.home.by_country

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.R

class CountryAdapter(var data: List<Area>, private var listener : CountryListener) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val country_name: TextView = row.findViewById(R.id.country_name)
        val countryItem : ConstraintLayout = row.findViewById(R.id.countryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.country_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = data.get(position)
        holder.country_name.text = currentCountry.strArea
        holder.countryItem.setOnClickListener{
            listener.onCountryClick(currentCountry)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}