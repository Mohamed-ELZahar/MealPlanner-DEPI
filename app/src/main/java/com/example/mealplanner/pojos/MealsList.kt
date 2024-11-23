package com.example.mealplanner.pojos

import com.google.gson.annotations.SerializedName


data class MealsList (

  @SerializedName("meals" ) var meals : ArrayList<SearchedMeal> = arrayListOf()

)