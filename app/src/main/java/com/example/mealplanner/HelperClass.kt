package com.example.mealplanner

import android.view.View
import com.example.mealplanner.calender.WeekDays
import com.example.mealplanner.meal_details.Ingredient
import com.example.mealplanner.pojos.Meals
import com.example.mealplanner.room_db.PlanMeals
import com.google.android.material.snackbar.Snackbar


class HelperClass {
    fun getURLOfIngredients(InName: String): String {
        val result: StringBuilder = StringBuilder("https://www.themealdb.com/images/ingredients/")
        result.append(InName).append("-Small.png")
        return result.toString()
    }

    fun setupIngredientList(meal: Meals): List<String> {
        val result: ArrayList<String> = arrayListOf()
        if (!meal.strIngredient1.isNullOrEmpty()) {
            result.add(meal.strIngredient1.toString())
        }
        if (!meal.strIngredient2.isNullOrEmpty()) {
            result.add(meal.strIngredient2.toString())
        }
        if (!meal.strIngredient3.isNullOrEmpty()) {
            result.add(meal.strIngredient3.toString())
        }
        if (!meal.strIngredient4.isNullOrEmpty()) {
            result.add(meal.strIngredient4.toString())
        }
        if (!meal.strIngredient5.isNullOrEmpty()) {
            result.add(meal.strIngredient5.toString())
        }
        if (!meal.strIngredient6.isNullOrEmpty()) {
            result.add(meal.strIngredient6.toString())
        }
        if (!meal.strIngredient7.isNullOrEmpty()) {
            result.add(meal.strIngredient7.toString())
        }
        if (!meal.strIngredient8.isNullOrEmpty()) {
            result.add(meal.strIngredient8.toString())
        }
        if (!meal.strIngredient9.isNullOrEmpty()) {
            result.add(meal.strIngredient9.toString())
        }
        if (!meal.strIngredient10.isNullOrEmpty()) {
            result.add(meal.strIngredient10.toString())
        }
        if (!meal.strIngredient11.isNullOrEmpty()) {
            result.add(meal.strIngredient11.toString())
        }
        if (!meal.strIngredient12.isNullOrEmpty()) {
            result.add(meal.strIngredient12.toString())
        }
        if (!meal.strIngredient13.isNullOrEmpty()) {
            result.add(meal.strIngredient13.toString())
        }
        if (!meal.strIngredient14.isNullOrEmpty()) {
            result.add(meal.strIngredient14.toString())
        }
        if (!meal.strIngredient15.isNullOrEmpty()) {
            result.add(meal.strIngredient15.toString())
        }
        if (!meal.strIngredient16.isNullOrEmpty()) {
            result.add(meal.strIngredient16.toString())
        }
        if (!meal.strIngredient17.isNullOrEmpty()) {
            result.add(meal.strIngredient17.toString())
        }
        if (!meal.strIngredient18.isNullOrEmpty()) {
            result.add(meal.strIngredient18.toString())
        }
        if (!meal.strIngredient19.isNullOrEmpty()) {
            result.add(meal.strIngredient19.toString())
        }
        if (!meal.strIngredient20.isNullOrEmpty()) {
            result.add(meal.strIngredient20.toString())
        }

        return result.toList()
    }

    fun setupsMeasureList(meal: Meals): List<String> {
        val result: ArrayList<String> = arrayListOf()

        if (!meal.strMeasure1.isNullOrEmpty()) {
            result.add(meal.strMeasure1.toString())
        }
        if (!meal.strMeasure2.isNullOrEmpty()) {
            result.add(meal.strMeasure2.toString())
        }
        if (!meal.strMeasure3.isNullOrEmpty()) {
            result.add(meal.strMeasure3.toString())
        }
        if (!meal.strMeasure4.isNullOrEmpty()) {
            result.add(meal.strMeasure4.toString())
        }
        if (!meal.strMeasure5.isNullOrEmpty()) {
            result.add(meal.strMeasure5.toString())
        }
        if (!meal.strMeasure6.isNullOrEmpty()) {
            result.add(meal.strMeasure6.toString())
        }
        if (!meal.strMeasure7.isNullOrEmpty()) {
            result.add(meal.strMeasure7.toString())
        }
        if (!meal.strMeasure8.isNullOrEmpty()) {
            result.add(meal.strMeasure8.toString())
        }
        if (!meal.strMeasure9.isNullOrEmpty()) {
            result.add(meal.strMeasure9.toString())
        }
        if (!meal.strMeasure10.isNullOrEmpty()) {
            result.add(meal.strMeasure10.toString())
        }
        if (!meal.strMeasure11.isNullOrEmpty()) {
            result.add(meal.strMeasure11.toString())
        }
        if (!meal.strMeasure12.isNullOrEmpty()) {
            result.add(meal.strMeasure12.toString())
        }
        if (!meal.strMeasure13.isNullOrEmpty()) {
            result.add(meal.strMeasure13.toString())
        }
        if (!meal.strMeasure14.isNullOrEmpty()) {
            result.add(meal.strMeasure14.toString())
        }
        if (!meal.strMeasure15.isNullOrEmpty()) {
            result.add(meal.strMeasure15.toString())
        }
        if (!meal.strMeasure16.isNullOrEmpty()) {
            result.add(meal.strMeasure16.toString())
        }
        if (!meal.strMeasure17.isNullOrEmpty()) {
            result.add(meal.strMeasure17.toString())
        }
        if (!meal.strMeasure18.isNullOrEmpty()) {
            result.add(meal.strMeasure18.toString())
        }
        if (!meal.strMeasure19.isNullOrEmpty()) {
            result.add(meal.strMeasure19.toString())
        }
        if (!meal.strMeasure20.isNullOrEmpty()) {
            result.add(meal.strMeasure20.toString())
        }

        return result.toList()
    }

    fun setIngredientList(meal: Meals): List<Ingredient> {
        val result: ArrayList<Ingredient> = arrayListOf()
        val ingredients: List<String> = setupIngredientList(meal)
        val measures: List<String> = setupsMeasureList(meal)

        for (i in 0..ingredients.size-1) {
            result.add(
                Ingredient(
                    strIngredient = ingredients.get(i)+": ",
                    strMeasure = measures.get(i),
                    strIngredientThumb = getURLOfIngredients(ingredients.get(i))
                )
            )

        }
        return result.toList()
    }

    fun setPlansToCalender(plans: List<PlanMeals>): List<WeekDays> {

        val data = ArrayList<WeekDays>()
        val sat = WeekDays(R.drawable.sat, "", "", "")
        val sun = WeekDays(R.drawable.sun, "", "", "")
        val mon = WeekDays(R.drawable.mon, "", "", "")
        val tue = WeekDays(R.drawable.tue, "", "", "")
        val wed = WeekDays(R.drawable.wed, "", "", "")
        val thu = WeekDays(R.drawable.thu, "", "", "")
        val fri = WeekDays(R.drawable.fri, "", "", "")
        plans.forEach {
            if (it.day.equals("Saturday")) {
                if (it.type.equals("Breakfast")) {
                    sat.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    sat.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    sat.dMeal = it.strMeal
                }
            }else if (it.day.equals("Sunday")) {
                if (it.type.equals("Breakfast")) {
                    sun.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    sun.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    sun.dMeal = it.strMeal
                }
            }else if (it.day.equals("Monday")) {
                if (it.type.equals("Breakfast")) {
                    mon.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    mon.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    mon.dMeal = it.strMeal
                }
            } else if (it.day.equals("Tuesday")) {
                if (it.type.equals("Breakfast")) {
                    tue.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    tue.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    tue.dMeal = it.strMeal
                }
            }else if (it.day.equals("Wednesday")) {
                if (it.type.equals("Breakfast")) {
                    wed.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    wed.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    wed.dMeal = it.strMeal
                }
            }else if (it.day.equals("Thursday")) {
                if (it.type.equals("Breakfast")) {
                    thu.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    thu.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    thu.dMeal = it.strMeal
                }
            }else if (it.day.equals("Friday")) {
                if (it.type.equals("Breakfast")) {
                    fri.bfMeal = it.strMeal
                }else if (it.type.equals("Launch")) {
                    fri.lMeal = it.strMeal
                }else if (it.type.equals("dinner")) {
                    fri.dMeal = it.strMeal
                }
            }
        }
        data.add(sat)
        data.add(sun)
        data.add(mon)
        data.add(tue)
        data.add(wed)
        data.add(thu)
        data.add(fri)
        return data.toList()
      }

    fun makeSnackBar(str: String, view: View){
        Snackbar.make(view, str, Snackbar.LENGTH_SHORT).show()
    }

    fun validatePassword(password: String): Boolean {
        if (password.length < 8) {
            // Password too short
            return false
        }

        if (!password.contains(Regex("[A-Z]"))) {
            // Missing uppercase letter
            return false
        }

        if (!password.contains(Regex("[a-z]"))) {
            // Missing lowercase letter
            return false
        }

        if (!password.contains(Regex("[0-9]"))) {
            // Missing number
            return false
        }

        // Add more validation rules as needed...

        return true
    }

}