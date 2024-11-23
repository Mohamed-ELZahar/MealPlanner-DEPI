package com.example.mealplanner.home.by_categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealplanner.R
import com.example.mealplanner.pojos.Category

class CategoriesRVAdapter(var data: List<Category>, private var catListener: CategoriesListener) :
    RecyclerView.Adapter<CategoriesRVAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val catName: TextView = row.findViewById(R.id.meal_title)
        val catThumb: ImageView = row.findViewById(R.id.meal_thumb)
        val cat_layout: ConstraintLayout = row.findViewById(R.id.cat_layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.cat_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCat: Category = data.get(position)
        Glide.with(context).load(currentCat.strCategoryThumb)
            .into(holder.catThumb)
        holder.catName.text = currentCat.strCategory
        holder.cat_layout.setOnClickListener {
            catListener.onCatClick(currentCat)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}