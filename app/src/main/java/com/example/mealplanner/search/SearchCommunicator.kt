package com.example.mealplanner.search

import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.CategoryName
import com.example.mealplanner.pojos.Ingredients

interface SearchCommunicator {
    fun categorySelect(cat: Category)
    fun countrySelect(country: Area)
    fun ingredientSelect(ingredient: Ingredients)


}