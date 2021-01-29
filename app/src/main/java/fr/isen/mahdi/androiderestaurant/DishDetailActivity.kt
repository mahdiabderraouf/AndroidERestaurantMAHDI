package fr.isen.mahdi.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.mahdi.androiderestaurant.databinding.ActivityDishDetailBinding

class DishDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dish_detail)
    }
}