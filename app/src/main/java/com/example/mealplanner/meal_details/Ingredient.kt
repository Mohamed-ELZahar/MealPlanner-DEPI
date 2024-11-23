package com.example.mealplanner.meal_details

import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("strIngredient") var strIngredient : String? = null,
    @SerializedName("strMeasure") var strMeasure : String? = null,
    @SerializedName("strIngredientThumb") var strIngredientThumb : String? = null
)
