package com.example.ai3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ai3.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recipesAdapter: RecipesAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipesAdapter
        }
    }

    private fun setupListeners() {
        binding.getRecipesButton.setOnClickListener {
            val ingredient = binding.ingredientEditText.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                getRecommendedRecipes(ingredient)
            } else {
                Log.d("MainActivity", "Ingredient is empty")
            }
        }
    }

    private fun getRecommendedRecipes(ingredient: String) {
        firestore.collection("recipes")
            .whereArrayContains("ingredients", ingredient)
            .get()
            .addOnSuccessListener { result ->
                val recipes = result.documents.map { document ->
                    document.toObject(Recipe::class.java) ?: Recipe()
                }
                updateRecyclerView(recipes)
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Failed to get recipes", e)
            }
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        recipesAdapter = RecipesAdapter(recipes)
        binding.recyclerView.adapter = recipesAdapter
        recipesAdapter.notifyDataSetChanged()
    }
}
