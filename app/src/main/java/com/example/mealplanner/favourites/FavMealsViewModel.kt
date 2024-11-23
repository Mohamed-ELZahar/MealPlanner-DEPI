package com.example.mealplanner.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavMealsViewModel(private val dao: MealDao) : ViewModel() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = firebaseAuth.currentUser

    private val _meals: MutableLiveData<List<FavMeals>> = MutableLiveData()
    val meals: LiveData<List<FavMeals>> = _meals

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    init {
        getData(user!!)
    }

    fun getData(user: FirebaseUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllMeals(user.email.toString())
            withContext(Dispatchers.Main) {
                _meals.postValue(list)
            }
        }
    }

    fun removeMeal(meal: FavMeals) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.removeMeal(meal)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue("${meal.strMeal} deleted from favorites")
                } else {
                    _message.postValue("${meal.strMeal} Couldn't be deleted to favorites")
                }
            }
            getData(user!!)
        }
    }
}

class FavFactory(private val dao: MealDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavMealsViewModel::class.java)) {
            return FavMealsViewModel (dao) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}