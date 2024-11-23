package com.example.mealplanner.pojos

import com.google.gson.annotations.SerializedName


data class CategoryList (

  @SerializedName("CategoryName" ) var meals : ArrayList<CategoryName> = arrayListOf()

)