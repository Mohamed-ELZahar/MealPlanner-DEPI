package com.example.mealplanner.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_table")
data class FavMeals(
    @PrimaryKey
    var idMeal: String,
    var strMeal: String,
    var strMealThumb: String,
    var userEmail: String
)
