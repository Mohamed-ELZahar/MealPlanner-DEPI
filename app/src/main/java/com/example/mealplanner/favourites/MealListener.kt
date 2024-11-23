package com.example.mealplanner.favourites

import com.example.mealplanner.room_db.FavMeals

interface MealListener {
    fun onMealClick(meal: FavMeals)
    fun onItemClick(meal: FavMeals)
}
