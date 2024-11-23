package com.example.mealplanner.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.example.mealplanner.home.by_categories.CategoriesListener
import com.example.mealplanner.home.by_categories.CategoriesRVAdapter
import com.example.mealplanner.home.by_country.CountryAdapter
import com.example.mealplanner.home.by_country.CountryListener
import com.example.mealplanner.internet_Connection.APIClient
import com.example.mealplanner.meals_display.MealsDisplayListener
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDao
import com.example.mealplanner.room_db.MealDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeFragment() : Fragment(), CategoriesListener, MealsDisplayListener, CountryListener {
    private lateinit var mealName: TextView
    private lateinit var mealThumb: ImageView
    private lateinit var btnFav: ImageButton
    private lateinit var catRecyclerView: RecyclerView
    private lateinit var countryRecyclerView: RecyclerView
    private lateinit var catAdapter: CategoriesRVAdapter
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var randMeal: ConstraintLayout
    private lateinit var favMeal: FavMeals
    private lateinit var viewModel: HomeViewModel
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var helperClass: HelperClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUI(view, requireContext())
        setupViewModel()

        viewModel.getAllCategoriesData()
        viewModel.category.observe(viewLifecycleOwner) {
            catAdapter.data = it
            catAdapter.notifyDataSetChanged()
        }

        viewModel.getRandomMeal()
        viewModel.meal.observe(viewLifecycleOwner) {
            val currentMeal = it.get(0)
            mealName.text = currentMeal.strMeal
            Glide.with(this)
                .load(it.get(0).strMealThumb)
                .into(mealThumb)
            favMeal = FavMeals(
                idMeal = it.get(0).idMeal.toString(),
                strMeal = it.get(0).strMeal.toString(),
                strMealThumb = it.get(0).strMealThumb.toString(),
                userEmail = user.email.toString()
            )
            randMeal.setOnClickListener {
                onMealClick(currentMeal)
            }
            btnFav.setOnClickListener {
                viewModel.addMeal(favMeal)
                helperClass.makeSnackBar("Meal Added To Favourites", this@HomeFragment.requireView())
            }
        }
        viewModel.getCountriesData()
        viewModel.country.observe(viewLifecycleOwner) {
            countryAdapter.data = it
            countryAdapter.notifyDataSetChanged()
        }

    }

    private fun initUI(view: View, context: Context) {
        //Setup UI
        mealName = view.findViewById(R.id.mealName)
        mealThumb = view.findViewById(R.id.mealThumb)
        btnFav = view.findViewById(R.id.btnFragmentFav)
        randMeal = view.findViewById(R.id.randMeal)

        //Setup Firebase
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser ?: user
        if (user.isAnonymous){
            btnFav.visibility=View.GONE
        }

        //Setup RecyclerViews
        //For Categories RecyclerView
        catRecyclerView = view.findViewById(R.id.rvCategories)
        catAdapter = CategoriesRVAdapter(listOf(), this)
        catRecyclerView.adapter = catAdapter
        catRecyclerView.layoutManager = GridLayoutManager(context, 2)
        //For Countries RecyclerView
        countryRecyclerView = view.findViewById(R.id.rvCountry)
        countryAdapter = CountryAdapter(listOf(), this)
        countryRecyclerView.adapter = countryAdapter
        countryRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        helperClass = HelperClass()
    }

    private fun setupViewModel() {
        val dao: MealDao = MealDatabase.getMealsDatabase(requireContext()).getMealDao()
        val retrofit = APIClient.retrofitService
        val factory = HomeFactory(retrofit, dao)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

    }

    override fun onCatClick(cat: Category) {
        val communicator: HomeCommunicator = activity as HomeCommunicator
        communicator.selectCat(cat)
    }

    override fun onCountryClick(country: Area) {
        val communicator: HomeCommunicator = activity as HomeCommunicator
        communicator.selectCountry(country)
    }

    override fun onMealClick(meal: Meals) {
        val communicator: HomeCommunicator = activity as HomeCommunicator
        communicator.selectRandom(meal)
    }


    override fun addMealToFavourites(meal: FavMeals) {
        TODO("Not yet implemented")
    }

}