package com.example.mealplanner.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.internet_Connection.APIClient
import com.example.mealplanner.meal_details.Ingredient
import com.example.mealplanner.meals_display.MealsDisplayActivity
import com.example.mealplanner.pojos.Area
import com.example.mealplanner.pojos.Category
import com.example.mealplanner.pojos.Countries
import com.google.android.material.button.MaterialButtonToggleGroup


class SearchFragment : Fragment() {

    private var searchFactoryList = ArrayList<String>()
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SearchViewModel
    private lateinit var listAdapter: ArrayAdapter<String>
    private lateinit var searchFactoryLV: ListView
    private lateinit var btnCat: Button
    private lateinit var btnArea: Button
    private lateinit var btnIngredients: Button
    private lateinit var selectedCriteria: String


    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view, requireContext())
        setUpModel()
        toggleButtonGroup.addOnButtonCheckedListener() { toggleButtonGroup, checkedId, isChecked ->
            if (isChecked) {
                when (toggleButtonGroup.checkedButtonId) {
                    R.id.btnCountry -> onCountrySelect()
                    R.id.btnIngredient -> onIngredientSelect()
                    R.id.btnCategory -> onCategorySelect()
                }
                searchFactoryLV.adapter = listAdapter

            } else {

                if (toggleButtonGroup.checkedButtonId == View.NO_ID) {
                    searchFactoryList.clear()
                    listAdapter.notifyDataSetChanged()
                }
            }

        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                filterResults(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterResults(newText)
                return true
            }
        })


        searchFactoryLV.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val intent = Intent(requireContext(), MealsDisplayActivity::class.java)
            intent.putExtra("title", selectedItem)
            intent.putExtra("criteria", selectedCriteria)
//            intent.putExtra("catDes", "Desc")
//            intent.putExtra("catThumb", "")
            startActivity(intent)
        }
    }


    private fun initUI(view: View, context: Context) {
        //initialize UI elements
        btnCat = view.findViewById(R.id.btnCategory)
        btnArea = view.findViewById(R.id.btnCountry)
        btnIngredients = view.findViewById(R.id.btnIngredient)
        searchView = view.findViewById(R.id.searchView)
        searchFactoryLV = view.findViewById(R.id.searchFactor)
        toggleButtonGroup = view.findViewById(R.id.toggleButtonGroup)
        progressBar = view.findViewById(R.id.progressBar)
        selectedCriteria = ""

        //initialize adapter
        searchFactoryList = arrayListOf()
        listAdapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            searchFactoryList
        )
    }

    private fun setUpModel() {
        val retrofit = APIClient.retrofitService
        val factory = SearchFactory(retrofit)
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        viewModel.getAllCategoriesData()
        viewModel.listAllCountries()
        viewModel.listAllIngredients()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onCountrySelect() {
        searchView.clearFocus()
        searchFactoryList.clear()
        listAdapter.notifyDataSetChanged()
        progressBar.visibility = View.VISIBLE
        viewModel.country.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            it.forEach {
                searchFactoryList.add(it.strArea!!)
                listAdapter.notifyDataSetChanged()
                searchFactoryLV.adapter = listAdapter
            }
        }
        selectedCriteria = "area"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onIngredientSelect() {
        searchView.clearFocus()
        searchFactoryList.clear()
        listAdapter.notifyDataSetChanged()
        progressBar.visibility = View.VISIBLE
        viewModel.ingredient.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            it.forEach {
                searchFactoryList.add(it.strIngredient!!)
                listAdapter.notifyDataSetChanged()
                searchFactoryLV.adapter = listAdapter
            }
        }
        selectedCriteria = "ingredient"
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onCategorySelect() {
        searchView.clearFocus()
        searchFactoryList.clear()
        listAdapter.notifyDataSetChanged()
        progressBar.visibility = View.VISIBLE
        viewModel.category.observe(viewLifecycleOwner) {
            progressBar.visibility = View.GONE
            it.forEach {
                searchFactoryList.add(it.strCategory!!)
                listAdapter.notifyDataSetChanged()
                searchFactoryLV.adapter = listAdapter
            }
        }
        selectedCriteria = "category"
    }

    private fun filterResults(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            // Show all results for the selected criteria if query is empty
            when (selectedCriteria) {
                "area" -> viewModel.country.value ?: emptyList()
                "ingredient" -> viewModel.ingredient.value ?: emptyList()
                "category" -> viewModel.category.value ?: emptyList()
                else -> emptyList()
            }
        } else {
            // Filter based on the query if it's not empty
            when (selectedCriteria) {
                "area" -> viewModel.country.value?.filter {
                    it.strArea?.contains(query, ignoreCase = true) ?: false
                } ?: emptyList()
                "ingredient" -> viewModel.ingredient.value?.filter {
                    it.strIngredient?.contains(query, ignoreCase = true) ?: false
                } ?: emptyList()
                "category" -> viewModel.category.value?.filter {
                    it.strCategory?.contains(query, ignoreCase = true) ?: false
                } ?: emptyList()
                else -> emptyList()
            }
        }

        // Update the list for the adapter
        searchFactoryList.clear()
        searchFactoryList.addAll(filteredList.map {
            when (selectedCriteria) {
                "area" -> (it as? Area)?.strArea ?: ""
                "ingredient" -> (it as? Ingredient)?.strIngredient ?: ""
                "category" -> (it as? Category)?.strCategory ?: ""
                else -> ""
            }
        })
        listAdapter.notifyDataSetChanged() // Notify the adapter about the changes
    }

}
