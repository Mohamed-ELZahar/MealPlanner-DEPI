package com.example.mealplanner.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.room_db.MealDao

class HomeFactory (private val retrofit: MealService, private val dao: MealDao
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(retrofit, dao ) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}