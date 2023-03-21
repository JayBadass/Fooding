package com.example.fooding.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.fooding.databinding.ActivityNutritionDetailBinding

class NutritionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNutritionDetailBinding

    private val args: NutritionDetailActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayout()
    }

    private fun setLayout() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.food = args.food
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}