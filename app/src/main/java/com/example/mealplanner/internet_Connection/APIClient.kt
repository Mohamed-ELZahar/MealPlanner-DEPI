package com.example.mealplanner.internet_Connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
object APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitService = retrofit.create(MealService::class.java)

}