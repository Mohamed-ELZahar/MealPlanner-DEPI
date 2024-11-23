package com.example.mealplanner.meals_display

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealsDisplayViewModel (private val dao: MealDao, private val retrofit: MealService) :
    ViewModel() {

    private val _meals: MutableLiveData<List<Meals>> = MutableLiveData()
    val meals: LiveData<List<Meals>> = _meals


    private val _areaMeals: MutableLiveData<List<Meals>> = MutableLiveData()
    val areaMeals: LiveData<List<Meals>> = _areaMeals


    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    init {
//        getData()
    }

    fun getMealByCat(catName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.getByCat(catName).body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {

                    _meals.postValue(list!!)
                }
            }
        }
    }

    fun getMealByCountry(countryName : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.searchByArea(countryName).body()?.meals
            val result : ArrayList<Meals> = arrayListOf()
            list?.forEach {
                result.add(Meals(strMeal = it.strMeal , idMeal = it.idMeal , strMealThumb = it.strMealThumb))
            }
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {

                    _areaMeals.postValue(result!!)
                }
            }
        }
    }
    fun getMealByIngredient(title : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.searchByIngredient(title).body()?.meals
            val result : ArrayList<Meals> = arrayListOf()
            list?.forEach {
                result.add(Meals(strMeal = it.strMeal , idMeal = it.idMeal , strMealThumb = it.strMealThumb))
            }
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {

                    _meals.postValue(result!!)
                }
            }
        }
    }

    fun addMeal(meal: FavMeals) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.addMeal(meal)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue("${meal.strMeal} Added to favorites")
                } else {
                    _message.postValue("${meal.strMeal} Couldn't be added to favorites")
                }
            }

        }

    }
}