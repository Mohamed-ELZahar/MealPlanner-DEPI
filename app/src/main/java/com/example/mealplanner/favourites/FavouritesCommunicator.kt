package com.example.mealplanner.favourites

import com.example.mealplanner.room_db.FavMeals

interface FavouritesCommunicator {
    fun selectFavMeal(meal: FavMeals)
}