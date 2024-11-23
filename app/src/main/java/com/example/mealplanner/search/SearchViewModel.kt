package com.example.mealplanner.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Ingredients
import com.example.mealplanner.pojos.SearchedMeal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val retrofit: MealService) : ViewModel() {

    private val _searchedMeal: MutableLiveData<List<SearchedMeal>> = MutableLiveData()
    val searchedMeal: LiveData<List<SearchedMeal>>
        get() = _searchedMeal

    private val _country: MutableLiveData<List<Area>> = MutableLiveData()
    val country: LiveData<List<Area>>
        get() = _country

    private val _ingredient: MutableLiveData<List<Ingredients>> = MutableLiveData()
    val ingredient: LiveData<List<Ingredients>>
        get() = _ingredient

    private val _category: MutableLiveData<List<Category>> = MutableLiveData()
    val category: LiveData<List<Category>>
        get() = _category

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message



    init {
//        listAllCountries()
//        getAllCategoriesData()
////    listAllCategories()
//        listAllIngredients()
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

    fun listAllCountries() {
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

    fun listAllIngredients() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.getAllIngredients("list").body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _ingredient.postValue(list!!)
                }
            }
        }
    }

    fun searchByArea(area: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.searchByArea(area).body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _searchedMeal.postValue(list!!)
                }
            }
        }
    }
}

class SearchFactory(private val retrofit: MealService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(retrofit) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}