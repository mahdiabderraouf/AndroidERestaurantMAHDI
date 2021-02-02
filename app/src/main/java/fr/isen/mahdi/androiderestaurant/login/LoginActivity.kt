package fr.isen.mahdi.androiderestaurant.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.mahdi.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.mahdi.androiderestaurant.network.RegisterResult
import fr.isen.mahdi.androiderestaurant.network.User
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    @SuppressLint("ShowToast")
    private fun login() {
        if(validateData()) {
            launchRequest()
        } else {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/login"
        val postData = createPostData()
        val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                Response.Listener { response ->
                    val userResult = GsonBuilder().create().fromJson(response.toString(), RegisterResult::class.java)
                    saveUser(userResult.data)
                },
                Response.ErrorListener { error ->
                    onFailure(error)
                }
        )
        queue.add(request)
    }

    private fun validateData(): Boolean {
        return binding.email.text?.isNotEmpty() == true &&
                binding.password.text?.count() ?: 0 >= 6
    }

    private fun onFailure(error: VolleyError) {
        Log.d("request", String(error.networkResponse.data))
    }

    private fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun createPostData(): JSONObject {
        val postData = JSONObject()
        postData.put("id_shop", "1")
        postData.put("email", binding.email.text)
        postData.put("password", binding.password.text)
        return postData
    }

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}