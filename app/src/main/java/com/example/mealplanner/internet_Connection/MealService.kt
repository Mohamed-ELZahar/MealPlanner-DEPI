package com.example.mealplanner.internet_Connection

import com.example.mealplanner.pojos.Countries
import com.example.mealplanner.pojos.Categories
import com.example.mealplanner.pojos.CategoryList
import com.example.mealplanner.pojos.IngList
import com.example.mealplanner.pojos.MealsList
import com.example.mealplanner.pojos.ReturnedMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealService {
    @GET("random.php")
    suspend fun getRandomMeal(): Response<ReturnedMeal>

    @GET("lookup.php")
    suspend fun getMealByID(@Query("i") mealID: String): Response<ReturnedMeal>

    //List all countries
    @GET("list.php")
    suspend fun getByCountry(@Query("a") country: String): Response<Countries>

    //List all ingredients
    @GET("list.php")
    suspend fun getAllIngredients(@Query("i") ingredient: String): Response<IngList>

    //List all categories
    @GET("list.php")
    suspend fun getCategoryList(@Query("c") category: String): Response<CategoryList>

    @GET("categories.php")
    suspend fun getAllCategories(): Response<Categories>

    @GET("filter.php")
    suspend fun searchByArea(@Query("a") area: String): Response<MealsList>

    @GET("filter.php")
    suspend fun searchByIngredient(@Query("i") ingredient: String): Response<ReturnedMeal>

    @GET("filter.php")
    suspend fun getByCat(@Query("c") cat: String): Response<ReturnedMeal>

    @GET("search.php")
    suspend fun searchByName(@Query("s") s: String): Response<ReturnedMeal>
}

