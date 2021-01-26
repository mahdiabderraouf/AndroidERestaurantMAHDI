package fr.isen.mahdi.androiderestaurant.category

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.mahdi.androiderestaurant.databinding.DishesCellBinding
import fr.isen.mahdi.androiderestaurant.network.Dish

class CategoryAdapter(private val dishes: List<Dish>): RecyclerView.Adapter<CategoryAdapter.DishesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesViewHolder {
        return DishesViewHolder(DishesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DishesViewHolder, position: Int) {
        holder.dishName.text = dishes[position].name.take(40)
        holder.dishPrice.text = dishes[position].prices[0].price + " $"
        if (dishes[position].images[0].isNotEmpty()) {
            Picasso.get().load(dishes[position].images[0]).into(holder.dishImage);
        }
    }

    override fun getItemCount(): Int {
        return dishes.count()
    }

    class DishesViewHolder(dishesBinding: DishesCellBinding): RecyclerView.ViewHolder(dishesBinding.root) {
        val dishName: TextView = dishesBinding.dishName
        val dishPrice: TextView = dishesBinding.dishPrice
        val dishImage: ImageView = dishesBinding.dishImage
    }
}