package com.example.mealplanner.startup_activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealplanner.R
import com.example.mealplanner.calender.CalenderCommunicator
import com.example.mealplanner.calender.CalenderFragment
import com.example.mealplanner.databinding.ActivityMainBinding
import com.example.mealplanner.favourites.FavouritesCommunicator
import com.example.mealplanner.favourites.FavouritesFragment
import com.example.mealplanner.guest.GuestFragment
import com.example.mealplanner.home.HomeCommunicator
import com.example.mealplanner.home.HomeFragment
import com.example.mealplanner.meal_details.MealDetails
import com.example.mealplanner.meals_display.MealsDisplayActivity
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Ingredients
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.pojos.SearchedMeal
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.search.SearchCommunicator
import com.example.mealplanner.search.SearchFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(), HomeCommunicator, FavouritesCommunicator,
    SearchCommunicator, CalenderCommunicator {
    private lateinit var auth: FirebaseAuth
    private lateinit var btnLogout: Button
    private lateinit var user: FirebaseUser
    private lateinit var welcomeUser: TextView
    private lateinit var binding: ActivityMainBinding
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.favourites -> if (user.isAnonymous) {
                    replaceFragment(GuestFragment())
                } else {
                    replaceFragment(FavouritesFragment())
                }
                R.id.calender -> if (user.isAnonymous) {
                    replaceFragment(GuestFragment())
                } else {
                    replaceFragment(CalenderFragment())
                }

                else -> {
                }
            }
            true

        }

        initUI()


        if (user == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            welcomeUser.text = user.email
        }



        if (user.isAnonymous) {
            btnLogout.text = "Signup"
            welcomeUser.text = "Hello Guest"
        }

//        welcomeUser.text = user.email
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun initUI() {
        auth = FirebaseAuth.getInstance()
        welcomeUser = findViewById(R.id.welcomeUser)
        btnLogout = findViewById(R.id.logOut)
        user = auth.currentUser ?: user

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


    override fun selectCat(cat: Category) {
        val intent = Intent(this, MealsDisplayActivity::class.java)
        intent.putExtra("title", cat.strCategory)
        intent.putExtra("catDes", cat.strCategoryDescription)
        intent.putExtra("catThumb", cat.strCategoryThumb)
        intent.putExtra("criteria", "category")
        startActivity(intent)
    }

    override fun mealCountrySelect(meal: SearchedMeal) {
        val intent = Intent(this, MealsDisplayActivity::class.java)
//        intent.putExtra("title", meal.)
        intent.putExtra("criteria", "category")
        startActivity(intent)
    }

    override fun selectRandom(meal: Meals) {
        val intent = Intent(this, MealDetails::class.java)
        intent.putExtra("mealID", meal.idMeal)
        startActivity(intent)
    }

    override fun selectCountry(country: Area) {
        val intent = Intent(this, MealsDisplayActivity::class.java)
        intent.putExtra("title", country.strArea)
        intent.putExtra("criteria", "area")
//        intent.putExtra("catDes", country.strCategoryDescription)
//        intent.putExtra("thumb", country.strMealThumb)
        startActivity(intent)
    }

    override fun selectFavMeal(meal: FavMeals) {
        val intent = Intent(this, MealDetails::class.java)
        intent.putExtra("mealID", meal.idMeal)
        startActivity(intent)
    }

    override fun categorySelect(cat: Category) {
        TODO("Not yet implemented")
    }

    override fun countrySelect(country: Area) {
        TODO("Not yet implemented")
    }

    override fun ingredientSelect(ingredient: Ingredients) {
        TODO("Not yet implemented")
    }

    //For Calender
    override fun selectMealTitle(mealName: String) {
        val intent = Intent(this, MealDetails::class.java)
        intent.putExtra("mealName", mealName)
        startActivity(intent)
    }

}