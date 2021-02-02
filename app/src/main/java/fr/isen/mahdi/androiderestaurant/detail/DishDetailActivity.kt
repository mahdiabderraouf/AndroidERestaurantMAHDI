package fr.isen.mahdi.androiderestaurant.detail

import PhotoSlideFragment
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.isen.mahdi.androiderestaurant.BaseActivity
import fr.isen.mahdi.androiderestaurant.databinding.ActivityDishDetailBinding
import fr.isen.mahdi.androiderestaurant.network.Dish
import fr.isen.mahdi.androiderestaurant.category.CategoryActivity.Companion.DISH
import fr.isen.mahdi.androiderestaurant.network.Basket
import fr.isen.mahdi.androiderestaurant.network.BasketItem


class DishDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDishDetailBinding
    private var imageCount = 0
    private var quantity = 1
    private lateinit var dish: Dish
    private var price: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = PhotoSlideAdapter(this)
        binding.imagesCarousel.adapter = pagerAdapter

        dish = intent.getSerializableExtra(DISH) as Dish
        imageCount = dish.images.count()
        price = dish.prices[0].price.toFloat()
        quantity = getCurrentDishQuantity(dish)

        setUpUI(dish)

        binding.btnIncrement.setOnClickListener {
            incrementQuantity()
        }
        binding.btnDecrement.setOnClickListener {
            decrementQuantity()
        }
        binding.btnAddToCart.setOnClickListener {
            addToBasket(dish, quantity)
        }
    }
    
    @SuppressLint("SetTextI18n")
    private fun setUpUI(dish: Dish) {
        binding.dishIngredients.text = dish.ingredients.joinToString {
            it.name
        }
        binding.dishDetailName.text = dish.name
        binding.dishDetailPrice.text = "$price $"
        binding.quantityTextView.text = quantity.toString()
        binding.btnAddToCart.text = "Total ${price * quantity} $"
    }

    private fun incrementQuantity() {
        quantity++
        updateUI()
    }

    private fun decrementQuantity() {
        if (quantity > 1) {
            quantity--
            updateUI()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.btnAddToCart.text = "Total ${price * quantity} $"
        binding.quantityTextView.text = quantity.toString()
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

    private fun addToBasket(dish: Dish, quantity: Int) {
        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, quantity))
        refreshMenu()
        basket.save(this)
    }

    private fun refreshMenu() {
        invalidateOptionsMenu()
    }

    private fun getCurrentDishQuantity(dish: Dish): Int {
        val basket = Basket.getBasket(this)
        val selectedDish = basket.items.firstOrNull {
            it.dish.name == dish.name
        }
        selectedDish?.let {
            return selectedDish.quantity
        }
        return 1
    }

    companion object {
        const val BASKET_COUNT = "BASKET_COUNT"
    }

    private inner class PhotoSlideAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = imageCount

        override fun createFragment(position: Int): Fragment = PhotoSlideFragment(dish.images[position])
    }
}