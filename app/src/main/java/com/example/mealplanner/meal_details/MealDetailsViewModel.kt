package com.example.mealplanner.meal_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.internet_Connection.MealService
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDao
import com.example.mealplanner.room_db.PlanDAO
import com.example.mealplanner.room_db.PlanMeals
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealDetailsViewModel(
    private val planDao: PlanDAO,
    private val dao: MealDao,
    private val retrofit: MealService
) :
    ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = firebaseAuth.currentUser

    private val _meals: MutableLiveData<List<Meals>> = MutableLiveData()
    val meals: LiveData<List<Meals>> = _meals

    private val _favMeals: MutableLiveData<List<FavMeals>> = MutableLiveData()
    val favMeals: LiveData<List<FavMeals>> = _favMeals

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    private val _currentMeal: MutableLiveData<Meals> = MutableLiveData()
    val currentMeal: LiveData<Meals> = _currentMeal

    init {
        getAllFavourite(user!!)
    }

    fun getMealByID(mealID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.getMealByID(mealID).body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _meals.postValue(list!!)
                    _currentMeal.postValue(list.get(0))
                }
            }
        }
    }

    fun getMealByName(mealName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = retrofit.searchByName(mealName).body()?.meals
            withContext(Dispatchers.Main) {
                if (list.isNullOrEmpty()) {
                    _message.postValue("Couldn't load data")
                } else {
                    _meals.postValue(list!!)
//                    _currentMeal.postValue(list.get(0))
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

    fun addPlan(plan: PlanMeals) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = planDao.addPlan(plan)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue("${plan.strMeal} Added to favorites")
                } else {
                    _message.postValue("${plan.strMeal} Couldn't be added to favorites")
                }
            }
        }
    }

    fun isFavourite(mealName: String): Boolean {
        var flag = false
        favMeals.value?.forEach {
            if (mealName == it.strMeal) {
                flag = true
                return@forEach
            }
        }
        return flag
    }

    fun getAllFavourite(user: FirebaseUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllMeals(user.email.toString())
            withContext(Dispatchers.Main) {
                _favMeals.postValue(list)
            }
        }
    }

    fun removeMeal(meal: FavMeals) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.removeMeal(meal)
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    _message.postValue("${meal.strMeal} Removed from favorites")
                } else {
                    _message.postValue("${meal.strMeal} Couldn't be removed from favorites")
                }
            }
        }
    }


}


