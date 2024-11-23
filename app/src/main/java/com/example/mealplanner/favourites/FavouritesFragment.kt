package com.example.mealplanner.favourites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FavouritesFragment : Fragment(), MealListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favAdapter: FavMealAdapter
    private lateinit var viewModel: FavMealsViewModel
    private lateinit var helperClass: HelperClass
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initUI(view, requireContext())
        setUpModel()

        viewModel.getData(user)
        viewModel.meals.observe(viewLifecycleOwner) {
            favAdapter.data = it
            favAdapter.notifyDataSetChanged()
        }
        viewModel

    }

    private fun initUI(view: View, context: Context) {
        recyclerView = view.findViewById(R.id.rvFavMeals)
        favAdapter = FavMealAdapter(listOf(), this)
        recyclerView.adapter = favAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        helperClass = HelperClass()
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser ?: user
    }

    private fun setUpModel() {
        val dao = MealDatabase.getMealsDatabase(requireContext()).getMealDao()
        val factory = FavFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(FavMealsViewModel::class.java)
    }

    override fun onMealClick(meal: FavMeals) {
        viewModel.removeMeal(meal)
        helperClass.makeSnackBar("Meal removed from favourites", requireView())

    }

    override fun onItemClick(meal: FavMeals) {
        val communicator: FavouritesCommunicator = activity as FavouritesCommunicator
        communicator.selectFavMeal(meal)
    }
}

