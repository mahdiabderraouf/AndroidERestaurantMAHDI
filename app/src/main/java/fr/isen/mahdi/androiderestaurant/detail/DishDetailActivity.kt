package fr.isen.mahdi.androiderestaurant.detail

import PhotoSlideFragment
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.mahdi.androiderestaurant.databinding.ActivityDishDetailBinding
import fr.isen.mahdi.androiderestaurant.network.Dish
import fr.isen.mahdi.androiderestaurant.category.CategoryActivity.Companion.DISH

class DishDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishDetailBinding

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private var imageCount = 0
    private var quantity = 0
    private lateinit var dish: Dish
    private var price = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = PhotoSlideAdapter(this)
        binding.imagesCarousel.adapter = pagerAdapter

        dish = intent.getSerializableExtra(DISH) as Dish
        price = dish.prices[0].price.toInt()
        imageCount = dish.images.count()

        binding.dishIngredients.text = dish.ingredients.joinToString {
            it.name
        }
        binding.dishDetailName.text = dish.name
        binding.dishDetailPrice.text = "$price $"
        binding.quantityTextView.text = quantity.toString()

        binding.btnIncrement.setOnClickListener {
            incrementQuantity()
        }
        binding.btnDecrement.setOnClickListener {
            decrementQuantity()
        }
    }

    private fun incrementQuantity() {
        quantity++
        updatePrice()
        binding.quantityTextView.text = quantity.toString()
    }

    private fun decrementQuantity() {
        if (quantity > 0) {
            quantity--
            updatePrice()
            binding.quantityTextView.text = quantity.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePrice() {
        binding.btnAddToCart.text = "Total ${price * quantity} $"
    }

    override fun onBackPressed() {
        if (binding.imagesCarousel.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.imagesCarousel.currentItem = binding.imagesCarousel.currentItem - 1
        }
    }

    private inner class PhotoSlideAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = imageCount

        override fun createFragment(position: Int): Fragment = PhotoSlideFragment(dish.images[position])
    }
}