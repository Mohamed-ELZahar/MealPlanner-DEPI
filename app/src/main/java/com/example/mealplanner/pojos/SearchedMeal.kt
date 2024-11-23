package com.example.mealplanner.pojos

import com.google.gson.annotations.SerializedName


data class SearchedMeal (

  @SerializedName("strMeal"      ) var strMeal      : String? = null,
  @SerializedName("strMealThumb" ) var strMealThumb : String? = null,
  @SerializedName("idMeal"       ) var idMeal       : String? = null

)