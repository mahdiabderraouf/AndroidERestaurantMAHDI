package fr.isen.mahdi.androiderestaurant

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.mahdi.androiderestaurant.category.CategoryActivity.Companion.USER_PREFERENCES_NAME
import fr.isen.mahdi.androiderestaurant.detail.DishDetailActivity
import fr.isen.mahdi.androiderestaurant.detail.DishDetailActivity.Companion.BASKET_COUNT
import fr.isen.mahdi.androiderestaurant.network.Basket

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
            // Start basket activity
        }
        return true
    }
}