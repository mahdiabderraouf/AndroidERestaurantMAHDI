package fr.isen.mahdi.androiderestaurant

import fr.isen.mahdi.androiderestaurant.network.Dish

interface CellClickListener {
    fun onCellClickListener(data: Dish)
}