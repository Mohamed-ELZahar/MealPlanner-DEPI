package com.example.mealplanner.meals_display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.room_db.MealDao

class MealsDisplayFactory (private val retrofit: MealService, private val dao: MealDao
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsDisplayViewModel::class.java)) {
            return MealsDisplayViewModel(dao,retrofit ) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}