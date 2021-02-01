package fr.isen.mahdi.androiderestaurant.detail

import fr.isen.mahdi.androiderestaurant.network.Dish

interface DishCellClickListener {
    fun onCellClickListener(data: Dish)
}