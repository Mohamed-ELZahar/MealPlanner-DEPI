package com.example.mealplanner.calender

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.example.mealplanner.home.HomeCommunicator
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.MealDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CalenderFragment : Fragment(), CalenderListener {
    private lateinit var viewModel: CalenderFragmentViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalenderAdapter
    private lateinit var helperClass: HelperClass
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth


    private lateinit var data: List<WeekDays>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view, requireContext())
        setUpModel()
        viewModel.getData(user)
        viewModel.plans.observe(viewLifecycleOwner) {
            data = helperClass.setPlansToCalender(plans = it)
            adapter = CalenderAdapter(data, this)
            recyclerView.adapter = adapter
        }

    }

    private fun initUI(view: View, context: Context) {
        recyclerView = view.findViewById(R.id.calRv)
        data = listOf()
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        helperClass = HelperClass()
    }

    private fun setUpModel() {
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser ?: user
        val dao = MealDatabase.getMealsDatabase(requireContext()).getPlanDao()
        val factory = PlanFactory(dao)
        viewModel = ViewModelProvider(this, factory).get(CalenderFragmentViewModel::class.java)
    }

    override fun onTitleClick(mealName: String) {
        val communicator: CalenderCommunicator = activity as CalenderCommunicator
        communicator.selectMealTitle(mealName)
    }

}

