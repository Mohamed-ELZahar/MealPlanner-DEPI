package com.example.mealplanner.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.R
import com.example.mealplanner.room_db.FavMeals

class FavMealAdapter(var data: List<FavMeals>, private val favListener: MealListener) :
    RecyclerView.Adapter<FavMealAdapter.FavMealViewHolder>() {

    private lateinit var context: Context

    class FavMealViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val mealThumbnail: ImageView = row.findViewById(R.id.meal_thumb)
        val mealTitle: TextView = row.findViewById(R.id.meal_title)
        val mealDetails : ConstraintLayout = row.findViewById(R.id.favourite_item)
        val btnFav: ImageButton = row.findViewById(R.id.btn_fav)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMealViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.favourite_item, parent, false)
        return FavMealViewHolder(view)
    }


    override fun onBindViewHolder(holder: FavMealViewHolder, position: Int) {
        val currentMeal: FavMeals = data.get(position)
        Glide.with(context).load(currentMeal.strMealThumb).into(holder.mealThumbnail)
        holder.mealTitle.text = currentMeal.strMeal
        holder.btnFav.setOnClickListener {
            favListener.onMealClick(currentMeal)
        }
        holder.mealDetails.setOnClickListener {
            favListener.onItemClick(currentMeal)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}