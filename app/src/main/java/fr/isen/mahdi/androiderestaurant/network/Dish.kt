package fr.isen.mahdi.androiderestaurant.network

import com.google.gson.annotations.SerializedName

class Dish (
    @SerializedName("name_fr") val name: String,
    val ingredients: List<Ingredient>,
    val images: List<String>,
    val prices: List<Price>
) {

}
