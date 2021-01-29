package fr.isen.mahdi.androiderestaurant

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import fr.isen.mahdi.androiderestaurant.category.CategoryActivity
import fr.isen.mahdi.androiderestaurant.category.ItemType
import fr.isen.mahdi.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var topToolbar: androidx.appcompat.widget.Toolbar

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