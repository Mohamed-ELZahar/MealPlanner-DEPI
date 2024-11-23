package com.example.mealplanner.meals_display

import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.pojos.SearchedMeal
import com.example.mealplanner.room_db.FavMeals

interface MealsDisplayListener {
    fun onMealClick(meal: Meals)
    fun addMealToFavourites (meal:FavMeals)

}