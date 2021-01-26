package fr.isen.mahdi.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.mahdi.androiderestaurant.category.CategoryActivity
import fr.isen.mahdi.androiderestaurant.category.ItemType
import fr.isen.mahdi.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entriesCardView.setOnClickListener {
            startCategoryActivity(ItemType.ENTRIES)
        }

        binding.dishesCardView.setOnClickListener {
            startCategoryActivity(ItemType.DISHES)
        }

        binding.dessetCardView.setOnClickListener {
            startCategoryActivity(ItemType.DESSERT)
        }

    }

    private fun startCategoryActivity(type: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra(CATEGORY_NAME, type)
        }
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}