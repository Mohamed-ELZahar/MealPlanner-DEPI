package com.example.mealplanner.home.by_categories

import com.example.mealplanner.pojos.Category

interface CategoriesListener {
    fun onCatClick(cat: Category)
}