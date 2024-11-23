package com.example.mealplanner.meal_details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.HelperClass
import com.example.mealplanner.R
import com.example.mealplanner.calender.BottomFragment
import com.example.mealplanner.internet_Connection.APIClient
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.FavMeals
import com.example.mealplanner.room_db.MealDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.Calendar

class MealDetails : AppCompatActivity() {
    private lateinit var nameOfTheMeal: TextView
    private lateinit var mealImage: ImageView
    private lateinit var originCountryName: TextView
    private lateinit var youtube: YouTubePlayerView
    private lateinit var steps: TextView
    private lateinit var addToFavsBtn: Button
    private lateinit var viewModel: MealDetailsViewModel
    private lateinit var mealID: String
    private lateinit var mealName: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientRVAdapter
    private lateinit var favMeal: FavMeals
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var helper: HelperClass
    private lateinit var addToPlanBtn: Button
    private lateinit var bottomSheetFragment: BottomFragment
    private var isFav: Boolean = true
    private lateinit var sendToCalender: Button

    @SuppressLint("SetJavaScriptEnabled", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal_details)

        var selectedMeal: Meals

        initUI()

        setUpModel()
        //mealId
        if (!mealID.isNullOrEmpty()) {
            viewModel.getMealByID(mealID)
            viewModel.meals.observe(this) {
                selectedMeal = it.get(0)
                updateUI(selectedMeal)

                addToPlanBtn.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("mealID", selectedMeal.idMeal.toString())
                    bottomSheetFragment.arguments = bundle
                    bottomSheetFragment.show(supportFragmentManager, "Tag")
                }
                isFav = viewModel.isFavourite(selectedMeal.strMeal.toString())

                if (isFav) {
                    addToFavsBtn.text = "Remove from Favourites"
                } else {
                    addToFavsBtn.text = "Add to Favourites"
                }
            }
        }
        //mealName
        if (!mealName.isNullOrEmpty()) {
            viewModel.getMealByName(mealName)
            viewModel.meals.observe(this) {
                selectedMeal = it.get(0)
                updateUI(selectedMeal)

                //Check favourite status
                isFav = viewModel.isFavourite(selectedMeal.strMeal.toString())
                if (isFav) {
                    addToFavsBtn.text = "Remove from Favourites"
                } else {
                    addToFavsBtn.text = "Add to Favourites"
                }
            }
        }

        if (isFav) {
            addToFavsBtn.text = "Remove from Favourites"
        } else {
            addToFavsBtn.text = "Add to Favourites"
        }


    }


    private fun initUI() {
        //Initializing UI
        sendToCalender = findViewById(R.id.sendCalender)

        addToPlanBtn = findViewById(R.id.addToPlanBtn)
        bottomSheetFragment = BottomFragment()
        nameOfTheMeal = findViewById(R.id.nameOfTheMeal)
        steps = findViewById(R.id.steps)
        mealImage = findViewById(R.id.imageOfTheMeal)
        originCountryName = findViewById(R.id.originCountryName)
        recyclerView = findViewById(R.id.ingredientRV)
        youtube = findViewById(R.id.youtube)
        addToFavsBtn = findViewById(R.id.addToFavsBtn)

        //Getting Intent
        val incomingIntent = intent
        mealName = incomingIntent.getStringExtra("mealName").toString()
        mealID = incomingIntent.getStringExtra("mealID").toString()

        //setting up recycler view
        ingredientAdapter = IngredientRVAdapter(listOf())
        recyclerView.adapter = ingredientAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Firebase
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser ?: user
        if (user.isAnonymous) {
            addToFavsBtn.visibility = View.GONE
            addToPlanBtn.visibility = View.GONE
            sendToCalender.visibility = View.GONE
        }
    }

    private fun setUpModel() {
        val retrofit = APIClient.retrofitService
        val dao = MealDatabase.getMealsDatabase(this).getMealDao()
        val planDao = MealDatabase.getMealsDatabase(this).getPlanDao()
        val factory = MealFactory(planDao, dao, retrofit)
        viewModel = ViewModelProvider(this, factory).get(MealDetailsViewModel::class.java)


    }

    @SuppressLint("SetJavaScriptEnabled", "NotifyDataSetChanged")
    private fun updateUI(incomingMeal: Meals) {
        lifecycle.addObserver(youtube)
        youtube.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                incomingMeal.strYoutube.let {
                    val videoId = Uri.parse(it).getQueryParameter("v")
                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                }
            }

        })


        //Meal Name
        nameOfTheMeal.text = incomingMeal.strMeal.toString()

        //Meal Thumbnail
        Glide.with(this)
            .load(incomingMeal.strMealThumb)
            .into(mealImage)

        //origin Country
        originCountryName.text = incomingMeal.strArea

        //Instructions
        steps.text = incomingMeal.strInstructions

        //Ingredients
        helper = HelperClass()
        ingredientAdapter.data = helper.setIngredientList(incomingMeal)
        ingredientAdapter.notifyDataSetChanged()

        //Favourites Button
        favMeal = FavMeals(
            idMeal = incomingMeal.idMeal.toString(),
            strMeal = incomingMeal.strMeal.toString(),
            strMealThumb = incomingMeal.strMealThumb.toString(),
            userEmail = user.email.toString()
        )
        addToFavsBtn.setOnClickListener {
            viewModel.getAllFavourite(user)
            isFav = viewModel.isFavourite(favMeal.strMeal)
            if (isFav) {
                viewModel.removeMeal(favMeal)
                addToFavsBtn.text = "Add to Favourites"
                helper.makeSnackBar(
                    "Meal removed from favourites",
                    this.findViewById(R.id.detailContainer)
                )

            } else {
                viewModel.addMeal(favMeal)
                addToFavsBtn.text = "Remove from Favourites"
                helper.makeSnackBar(
                    "Meal added to favourites",
                    this.findViewById(R.id.detailContainer)
                )
            }
        }
        sendToCalender.setOnClickListener {
            addEventToCalendar(incomingMeal.strMeal.toString())
        }
    }

    private fun addEventToCalendar(mealName: String) {
        val calendar = Calendar.getInstance()
        calendar.set(2024, Calendar.SEPTEMBER, 15, 9, 0)

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, mealName)// meal name
//            putExtra(CalendarContract.Events.EVENT_LOCATION, "Office")
//            putExtra(CalendarContract.Events.DESCRIPTION, "Discuss project details")
//            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.timeInMillis)
//            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calendar.timeInMillis + 60 * 60 * 1000) // 1 hour duration
            putExtra(CalendarContract.Events.ALL_DAY, true)//true
//            putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }else{
            helper.makeSnackBar("No calendar app found",this.findViewById(R.id.detailContainer))
        }
    }

}

