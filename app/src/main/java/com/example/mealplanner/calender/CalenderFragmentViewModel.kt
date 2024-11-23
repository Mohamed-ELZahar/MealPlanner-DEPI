package com.example.mealplanner.calender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mealplanner.room_db.PlanDAO
import com.example.mealplanner.room_db.PlanMeals
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalenderFragmentViewModel(private val dao: PlanDAO) : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = firebaseAuth.currentUser

    private val _plans: MutableLiveData<List<PlanMeals>> = MutableLiveData()
    val plans: LiveData<List<PlanMeals>> = _plans

    private val _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = _message

    init {
        getData(user!!)
    }

    fun getData(user: FirebaseUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = dao.getAllPlan(user.email.toString())
            withContext(Dispatchers.Main) {
                _plans.postValue(list)
            }
        }
    }

   }
class PlanFactory(private val dao: PlanDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalenderFragmentViewModel::class.java)) {
            return CalenderFragmentViewModel(dao) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}