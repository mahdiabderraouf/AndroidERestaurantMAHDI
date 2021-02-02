package fr.isen.mahdi.androiderestaurant.category

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import com.google.gson.GsonBuilder
import fr.isen.mahdi.androiderestaurant.BaseActivity
import fr.isen.mahdi.androiderestaurant.detail.DishCellClickListener
import fr.isen.mahdi.androiderestaurant.detail.DishDetailActivity
import fr.isen.mahdi.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.mahdi.androiderestaurant.R
import fr.isen.mahdi.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.mahdi.androiderestaurant.network.Basket.Companion.USER_PREFERENCES_NAME
import fr.isen.mahdi.androiderestaurant.network.Dish
import fr.isen.mahdi.androiderestaurant.network.MenuResult
import fr.isen.mahdi.androiderestaurant.utils.Loader
import org.json.JSONObject


enum class ItemType {
    ENTRIES, DISHES, DESSERT
}

class CategoryActivity : BaseActivity(),
    DishCellClickListener {
    private lateinit var binding: ActivityCategoryBinding
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = binding.categoryTitleTextView

        val selectedCategory: ItemType? = intent.getSerializableExtra(CATEGORY_NAME) as ItemType?

        binding.swipeLayout.setOnRefreshListener {
            resetCache()
            loadList(selectedCategory)
        }

        title.text = getCategoryTitle(selectedCategory)

        loadList(selectedCategory)

    }

    private fun loadList(category: ItemType?) {
        resultFromCache()?.let {
            // La requete est en cache
            onSuccess(parseResult(it, category))
        } ?: run {
            val loader = Loader()
            loader.show(this, "récupération du menu")
            val queue = Volley.newRequestQueue(this)
            val url = "http://test.api.catering.bluecodegames.com/menu"
            val postData = JSONObject()
            postData.put("id_shop", "1")
            val request = JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    postData,
                    Response.Listener { response ->
                        binding.swipeLayout.isRefreshing = false
                        cacheResult(response.toString())
                        loader.hide(this)
                        onSuccess(parseResult(response.toString(), category))
                    },
                    Response.ErrorListener { error ->
                        loader.hide(this)
                        binding.swipeLayout.isRefreshing = false
                        onFailure(error)
                    }
            )
            queue.add(request)
        }
    }

    private fun resultFromCache(): String? {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(REQUEST_CACHE, null)
    }

    private fun parseResult(response: String, selectedItem: ItemType?): List<Dish>? {
        val menuResult = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == getCategoryTitleFR(selectedItem) }
        return items?.items
    }

    private fun cacheResult(response: String) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(REQUEST_CACHE, response)
        editor.apply()
    }

    private fun resetCache() {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(REQUEST_CACHE)
        editor.apply()
    }

    private fun onSuccess(dishes: List<Dish>?) {
        dishes?.let {
            // CellClickListener = this because this implements CellClickListener
            val adapter = CategoryAdapter(it, this)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun onFailure(error: VolleyError) {
        Log.d("Request", error.toString())
    }

    override fun onCellClickListener(data: Dish) {
        val intent = Intent(this, DishDetailActivity::class.java).apply {
            putExtra(DISH, data)
        }
        startActivity(intent)
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.ENTRIES -> getString(R.string.entries)
            ItemType.DISHES -> getString(R.string.dishes)
            ItemType.DESSERT -> getString(R.string.dessert)
            else -> ""
        }
    }

    private fun getCategoryTitleFR(item: ItemType?): String {
        return when(item) {
            ItemType.ENTRIES -> "Entrées"
            ItemType.DISHES -> "Plats"
            ItemType.DESSERT -> "Desserts"
            else -> ""
        }
    }

    companion object {
        const val DISH = "DISH"
        const val REQUEST_CACHE = "REQUEST_CACHE"
    }
}
