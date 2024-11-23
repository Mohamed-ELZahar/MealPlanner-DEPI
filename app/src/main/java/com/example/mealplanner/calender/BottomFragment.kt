package com.example.mealplanner.calender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.internet_Connection.APIClient
import com.example.mealplanner.meal_details.MealDetailsViewModel
import com.example.mealplanner.meal_details.MealFactory
import com.example.mealplanner.room_db.MealDatabase
import com.example.mealplanner.room_db.PlanMeals
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class BottomFragment() : BottomSheetDialogFragment() {
    private lateinit var spinnerDays: Spinner
    private lateinit var spinnerType: Spinner
    private lateinit var confirmBtn: Button
    private lateinit var viewModel: MealDetailsViewModel
    private lateinit var days: Array<String>
    private lateinit var mealType: Array<String>
    private var mealID: String = ""
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
        mealID = arguments?.getString("mealID").toString()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        var plan: PlanMeals = PlanMeals(0, "", "", "", "", "",user.email.toString())
        setUpModel()
        viewModel.getMealByID(mealID)
        viewModel.currentMeal.observe(this) {
            plan.strMeal = it.strMeal
            plan.idMeal = it.idMeal
            plan.strMealThumb = it.strMealThumb

        }

        spinnerDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Assuming 'days' is a list or array of items
                plan.day = days[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected if necessary
                // For example, you might want to set a default value or null
                // plan.day = null
            }
        }
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Assuming 'days' is a list or array of items
                plan.type = mealType[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle the case when nothing is selected if necessary
                // For example, you might want to set a default value or null
                // plan.day = null
            }
        }


        confirmBtn.setOnClickListener {
            viewModel.addPlan(plan)
            dismiss()
        }

    }

    private fun initUI(view: View) {
        spinnerDays = view.findViewById(R.id.spinner_days)
        spinnerType = view.findViewById(R.id.spinner_type)
        confirmBtn = view.findViewById(R.id.btnOk)
        days =
            arrayOf("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
        val daysAdp =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, days)
        spinnerDays.adapter = daysAdp

        mealType = arrayOf("Breakfast", "Launch", "dinner")
        val mTypeAdp =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, mealType)
        spinnerType.adapter = mTypeAdp

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser ?: user


    }

    private fun setUpModel() {
        val retrofit = APIClient.retrofitService
        val dao = MealDatabase.getMealsDatabase(requireContext()).getMealDao()
        val planDao = MealDatabase.getMealsDatabase(requireContext()).getPlanDao()
        val factory = MealFactory(planDao, dao, retrofit)
        viewModel = ViewModelProvider(this, factory).get(MealDetailsViewModel::class.java)

    }
}