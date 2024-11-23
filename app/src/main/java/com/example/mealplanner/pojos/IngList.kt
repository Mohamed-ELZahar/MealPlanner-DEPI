package com.example.mealplanner.pojos

import com.google.gson.annotations.SerializedName


data class IngList (

  @SerializedName("meals" ) var meals : ArrayList<Ingredients> = arrayListOf()

)