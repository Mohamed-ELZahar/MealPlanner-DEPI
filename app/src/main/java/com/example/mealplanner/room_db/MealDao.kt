package com.example.mealplanner.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealDao {

    @Query("SELECT * FROM meal_table where userEmail= :userId")
    suspend fun getAllMeals(userId: String): List<FavMeals>
//
//    @Query("SELECT * FROM meal_table")
//    suspend fun getAllMeals(): List<FavMeals>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMeal(meals: FavMeals): Long

    @Delete
    suspend fun removeMeal(meals: FavMeals): Int
}