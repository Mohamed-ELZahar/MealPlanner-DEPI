package com.example.mealplanner.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDao
import com.example.mealplanner.room_db.MealDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val retrofit: MealService, private val dao: MealDao
) : ViewModel() {

    private val _category: MutableLiveData<List<Category>> = MutableLiveData()
    val category: LiveData<List<Category>>
        get() = _category

    private val _meal: MutableLiveData<List<Meals>> = MutableLiveData()
    val meal: LiveData<List<Meals>>
        get() = _meal

    private val _country: MutableLiveData<List<Area>> = MutableLiveData()
    val country: LiveData<List<Area>>
        get() = _country

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message

    init {
        getRandomMeal()
        getAllCategoriesData()
    }

    fun getAllCategoriesData() {
        viewModelScope.launch {
            val list = retrofit.getAllCategories().body()?.categories?.toList()
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _category.postValue(list!!)
                }
            }
        }
    }

    fun getRandomMeal() {
        viewModelScope.launch {
            val list = retrofit.getRandomMeal().body()?.meals?.toList()
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _meal.postValue(list!!)
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

    fun getCountriesData() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.getByCountry("list").body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {

                    _country.postValue(list!!)
                }
            }
        }
    }

}

