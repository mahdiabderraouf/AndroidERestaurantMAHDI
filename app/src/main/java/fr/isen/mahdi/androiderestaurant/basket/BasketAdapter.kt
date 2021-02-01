package fr.isen.mahdi.androiderestaurant.basket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.mahdi.androiderestaurant.databinding.BasketCellBinding
import fr.isen.mahdi.androiderestaurant.network.BasketItem

class BasketAdapter(private val items: List<BasketItem>, private val basketCellClickListener: BasketCellClickListener): RecyclerView.Adapter<BasketAdapter.BasketItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketItemsViewHolder {
        return BasketItemsViewHolder(BasketCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BasketItemsViewHolder, position: Int) {
        holder.dishName.text = items[position].dish.name.take(40)
        holder.dishPrice.text = "${items[position].dish.prices[0].price.toFloat() * items[position].quantity} $"
        if (items[position].dish.images[0].isNotEmpty()) {
            Picasso.get().load(items[position].dish.images[0]).into(holder.dishImage)
        }
        holder.btnDelete.setOnClickListener {
            basketCellClickListener.onDeleteItem(items[position])
        }
        holder.dishQuantity.text = "${items[position].quantity} * ${items[position].dish.prices[0].price.toFloat()}"
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class BasketItemsViewHolder(basketCellBinding: BasketCellBinding): RecyclerView.ViewHolder(basketCellBinding.root) {
        val dishName: TextView = basketCellBinding.dishName
        val dishPrice: TextView = basketCellBinding.dishPrice
        val dishImage: ImageView = basketCellBinding.dishImage
        val dishQuantity: TextView = basketCellBinding.dishQuantity
        val btnDelete: TextView = basketCellBinding.btnDelete
    }
}