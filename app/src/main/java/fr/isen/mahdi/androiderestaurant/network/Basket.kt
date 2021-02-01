package fr.isen.mahdi.androiderestaurant.network

import android.content.Context
import com.google.gson.GsonBuilder
import java.io.File
import java.io.Serializable

class Basket (private val items: MutableList<BasketItem>): Serializable {
    var count: Int = 0
        get() {
            if (this.items.count() > 0) {
                return this.items.map { it.quantity }.reduce { acc, i -> acc + i }
            }
            return field
        }

    fun addItem(item: BasketItem) {
        val existingItem = items.firstOrNull{
            it.dish.name == item.dish.name
        }
        existingItem?.let {
            existingItem.quantity += item.quantity
        } ?: run {
            items.add(item)
        }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            if (jsonFile.exists()) {
                val json = jsonFile.readText()
                return GsonBuilder().create().fromJson(json, Basket::class.java)
            }
            return Basket(mutableListOf())
        }
        const val BASKET_FILE = "basket.json"
    }
}