package fr.isen.mahdi.androiderestaurant.basket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.mahdi.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.mahdi.androiderestaurant.network.Basket
import fr.isen.mahdi.androiderestaurant.network.BasketItem

class BasketActivity : AppCompatActivity(),
    BasketCellClickListener {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reloadData()
    }

    private fun reloadData() {
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(Basket.getBasket(this).items, this)
    }

    override fun onDeleteItem(basketItem: BasketItem) {
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull {
            it.dish.name == basketItem.dish.name
        }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData()
    }
}