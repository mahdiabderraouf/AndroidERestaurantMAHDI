package fr.isen.mahdi.androiderestaurant

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.mahdi.androiderestaurant.basket.BasketActivity
import fr.isen.mahdi.androiderestaurant.network.Basket.Companion.BASKET_COUNT
import fr.isen.mahdi.androiderestaurant.network.Basket.Companion.USER_PREFERENCES_NAME

open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuView = menu.findItem(R.id.basket).actionView
        val countText = menuView.findViewById(R.id.basketCount) as TextView

        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val count = sharedPreferences.getInt(BASKET_COUNT, 0)

        countText.isVisible = count > 0
        countText.text = count.toString()

        menuView.setOnClickListener{
            intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }
        return true
    }
}