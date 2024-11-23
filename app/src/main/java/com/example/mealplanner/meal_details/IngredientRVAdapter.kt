package com.example.mealplanner.meal_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.R


class IngredientRVAdapter(var data: List<Ingredient>) :
    RecyclerView.Adapter<IngredientRVAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val ingredient: TextView = row.findViewById(R.id.ingredientName)
        val measure: TextView = row.findViewById(R.id.measureStr)
        val ingredientThumb: ImageView = row.findViewById(R.id.ingredientImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIngredient = data.get(position)
        holder.ingredient.text = currentIngredient.strIngredient.toString()
        holder.measure.text = currentIngredient.strMeasure.toString()

        Glide.with(context).load(currentIngredient.strIngredientThumb)
            .into(holder.ingredientThumb)

    }

    override fun getItemCount(): Int {
        return data.size

    }
}