package com.example.mealplanner.home

import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.pojos.SearchedMeal

interface HomeCommunicator {
    fun selectCat(cat:Category)
    fun selectRandom(meal: Meals)
    fun selectCountry(country: Area)
    fun mealCountrySelect (meal: SearchedMeal)
}