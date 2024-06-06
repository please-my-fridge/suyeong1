package com.example.ai3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ai3.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()

        val recipe: Recipe? = intent.getParcelableExtra("recipe")
        if (recipe != null) {
            binding.recipe = recipe
            binding.stepsTextView.text = recipe.steps.joinToString("\n") { step -> step }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
