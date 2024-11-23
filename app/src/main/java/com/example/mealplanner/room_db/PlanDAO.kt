package com.example.mealplanner.room_db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PlanDAO {


    @Query("SELECT * FROM plan_table where userEmail= :userId")
    suspend fun getAllPlan(userId: String): List<PlanMeals>

//    @Query("SELECT * FROM plan_table")
//    suspend fun getAllPlan(): List<PlanMeals>

    @Upsert
    suspend fun addPlan(planMeals: PlanMeals): Long

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addPlan(plan: PlanMeals): Long
//
//    @Delete
//    suspend fun removePlan(Plan: PlanMeals): Int
}