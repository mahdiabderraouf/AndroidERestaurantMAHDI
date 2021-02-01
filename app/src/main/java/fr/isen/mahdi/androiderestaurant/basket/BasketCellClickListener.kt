package fr.isen.mahdi.androiderestaurant.basket

import fr.isen.mahdi.androiderestaurant.network.BasketItem

interface BasketCellClickListener {
    fun onDeleteItem(basketItem: BasketItem)
}