package fr.isen.mahdi.androiderestaurant

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.isen.mahdi.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME


enum class ItemType {
    ENTRIES, DISHES, DESSERT
}

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val title = findViewById<TextView>(R.id.categoryTitleTextView)

        val category: ItemType? = intent.getSerializableExtra(CATEGORY_NAME) as ItemType?

        title.text = category.toString().toLowerCase().capitalize()
    }
}