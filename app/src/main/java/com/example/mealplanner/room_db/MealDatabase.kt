package com.example.mealplanner.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(FavMeals::class, PlanMeals::class), version = 1)
abstract class MealDatabase : RoomDatabase(){
    abstract fun getMealDao(): MealDao
    abstract fun getPlanDao(): PlanDAO
    companion object{
        @Volatile
        private var INSTANCE: MealDatabase? = null
        fun getMealsDatabase(ctx: Context): MealDatabase {
            return INSTANCE ?: synchronized(this){
                val tempInstance = Room.databaseBuilder(ctx, MealDatabase::class.java, "meals_db").build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }
}