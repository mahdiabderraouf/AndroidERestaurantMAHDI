package fr.isen.mahdi.androiderestaurant.basket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.mahdi.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.mahdi.androiderestaurant.network.Basket
import fr.isen.mahdi.androiderestaurant.network.BasketItem
import fr.isen.mahdi.androiderestaurant.register.RegisterActivity

class BasketActivity : AppCompatActivity(),
    BasketCellClickListener {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCommande.setOnClickListener  {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
        }

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

    private fun sendOrder(user_id: Int) {
        val message = Basket.getBasket(this).items.map {
            it.dish.name + " " + it.quantity
        }.joinToString()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.REQUEST_CODE) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val id_user = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if (id_user != -1) {
                sendOrder(id_user)
            }
        }
    }
}