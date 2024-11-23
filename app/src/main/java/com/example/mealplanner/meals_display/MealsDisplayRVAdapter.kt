package com.example.mealplanner.meals_display

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
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MealsDisplayRVAdapter(
    var data: List<Meals>,
    private var mealsDisplayListener: MealsDisplayListener
) :
    RecyclerView.Adapter<MealsDisplayRVAdapter.ViewHolder>() {

    private lateinit var context: Context
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: FirebaseUser =  auth.currentUser!!


    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val mealName: TextView = row.findViewById(R.id.meal_title)
        val mealThumb: ImageView = row.findViewById(R.id.meal_thumb)
        val mealDetails: ConstraintLayout = row.findViewById(R.id.meal_item)
        val btnFav: ImageButton = row.findViewById(R.id.btnFragmentFav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.meal_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMeal: Meals = data[position]

        val currentFavMeal = FavMeals(
            idMeal = data[position].idMeal.toString(),
            strMeal = data[position].strMeal.toString(),
            strMealThumb = data[position].strMealThumb.toString(),
            userEmail = user.email.toString()
        )

        Glide.with(context).load(currentMeal.strMealThumb)
            .into(holder.mealThumb)
        holder.mealName.text = currentMeal.strMeal

        if (user.isAnonymous){
            holder.btnFav.visibility = View.GONE
        }else{
            holder.btnFav.setOnClickListener {
                mealsDisplayListener.addMealToFavourites(currentFavMeal)
            }
        }

        holder.mealDetails.setOnClickListener {
            mealsDisplayListener.onMealClick(currentMeal)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}