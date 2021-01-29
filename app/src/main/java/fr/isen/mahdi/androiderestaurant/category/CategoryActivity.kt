package fr.isen.mahdi.androiderestaurant.category

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import com.google.gson.GsonBuilder
import fr.isen.mahdi.androiderestaurant.HomeActivity.Companion.CATEGORY_NAME
import fr.isen.mahdi.androiderestaurant.R
import fr.isen.mahdi.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.mahdi.androiderestaurant.network.MenuResult
import org.json.JSONObject


enum class ItemType {
    ENTRIES, DISHES, DESSERT
}

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = findViewById<TextView>(R.id.categoryTitleTextView)

        val selectedCategory: ItemType? = intent.getSerializableExtra(CATEGORY_NAME) as ItemType?

        title.text = getCategoryTitle(selectedCategory)

        loadList(selectedCategory)

    }

    private fun loadList(category: ItemType?) {
        // Instantiate the cache
        val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        val network = BasicNetwork(HurlStack())
        val queue = RequestQueue(cache, network).apply {
            start()
        }
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val postData = JSONObject()
        postData.put("id_shop", "1")
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            postData,
            Response.Listener { response ->
                onSuccess(response, category)
            },
            Response.ErrorListener { error ->
                onFailure(error)
            }
        )
        queue.add(request)
    }

    private fun onSuccess(response: JSONObject, category: ItemType?) {
        val menuResult = parseResult(response)
        val selectedCategory = menuResult?.data?.firstOrNull {
            it.name == getCategoryTitleFR(category)
        }
        if (selectedCategory != null) {
            val adapter = CategoryAdapter(selectedCategory.items)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun parseResult(response: JSONObject): MenuResult? {
        return GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
    }

    private fun onFailure(error: VolleyError) {
        Log.d("Request", error.toString())
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
}
