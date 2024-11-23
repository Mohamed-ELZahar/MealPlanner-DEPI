package com.example.mealplanner.room_db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "plan_table")
data class PlanMeals(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var idMeal: String?,
    var strMeal: String?,
    var strMealThumb: String?,
    var day: String?,
    var type: String?,
    var userEmail: String
)
