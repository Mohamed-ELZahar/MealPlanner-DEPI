package com.example.mealplanner.pojos

import com.google.gson.annotations.SerializedName


data class ReturnedMeal (

  @SerializedName("meals" ) var meals : ArrayList<Meals> = arrayListOf()

)