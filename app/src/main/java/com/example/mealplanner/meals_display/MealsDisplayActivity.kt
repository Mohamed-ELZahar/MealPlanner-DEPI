package com.example.mealplanner.meals_display

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.example.mealplanner.internet_Connection.APIClient
import com.example.mealplanner.meal_details.MealDetails
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDatabase

class MealsDisplayActivity : AppCompatActivity(), MealsDisplayListener {
    private lateinit var catTitle: TextView
    private lateinit var catThumbInside: ImageView
    private lateinit var viewModel: MealsDisplayViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var mealsDisplayRVAdapter: MealsDisplayRVAdapter
    private lateinit var helperClass: HelperClass



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_displayed_meals)
        initUI()
        setupViewModel()

        //Get the intent
        val incomingIntent = intent
        val title : String = incomingIntent.getStringExtra("title").toString()
        when (incomingIntent.getStringExtra("criteria").toString()) {
            "area" -> getAreaMeals(title)
            "category" -> getCategoryMeals(title)
            "ingredient" -> getIngredientMeals(title)
        }
    }

    private fun initUI() {
        //Initialize the views
        catTitle = findViewById(R.id.displayTitle)
        catThumbInside = findViewById(R.id.displayThumb)

        //Get the intent from HomeFragment
        val incomingIntent = intent
        catTitle.text = incomingIntent.getStringExtra("title").toString()
        Glide.with(this).load(incomingIntent.getStringExtra("catThumb").toString())
            .into(catThumbInside)

        //Initialize the recycler view
        recyclerView = findViewById(R.id.rvDisplayedMeals)
        mealsDisplayRVAdapter = MealsDisplayRVAdapter(listOf(), this)
        recyclerView.adapter = mealsDisplayRVAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        helperClass = HelperClass()
    }

    private fun setupViewModel() {
        val retrofit = APIClient.retrofitService
        val dao = MealDatabase.getMealsDatabase(this).getMealDao()
        val factory = MealsDisplayFactory(retrofit, dao)
        viewModel = ViewModelProvider(this, factory).get(MealsDisplayViewModel::class.java)

    }

    override fun onMealClick(meal: Meals) {
        val intent = Intent(this, MealDetails::class.java)
        intent.putExtra("mealID", meal.idMeal)
        intent.putExtra("mealName", meal.strMeal)
        startActivity(intent)
    }



    override fun addMealToFavourites(meal: FavMeals) {
        viewModel.addMeal(meal)
        helperClass.makeSnackBar("Added to favourites", this.findViewById(R.id.display_container))
    }

    private fun getAreaMeals(title: String) {
        viewModel.getMealByCountry(title)
        viewModel.areaMeals.observe(this) {
            mealsDisplayRVAdapter.data = it
            mealsDisplayRVAdapter.notifyDataSetChanged()
        }
    }

    private fun getCategoryMeals(title: String) {
        viewModel.getMealByCat(title)
        viewModel.meals.observe(this) {
            mealsDisplayRVAdapter.data = it
            mealsDisplayRVAdapter.notifyDataSetChanged()
        }
    }

    private fun getIngredientMeals(title: String) {
        viewModel.getMealByIngredient(title)
        viewModel.meals.observe(this) {
            mealsDisplayRVAdapter.data = it
            mealsDisplayRVAdapter.notifyDataSetChanged()
        }
    }
}