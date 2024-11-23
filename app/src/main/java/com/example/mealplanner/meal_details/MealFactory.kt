package com.example.mealplanner.meal_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.room_db.MealDao
import com.example.mealplanner.room_db.PlanDAO

class MealFactory(private val planDao: PlanDAO,private val dao: MealDao, private val retrofit: MealService) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)) {
            return MealDetailsViewModel(planDao,dao, retrofit) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}