package fr.isen.mahdi.androiderestaurant.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import fr.isen.mahdi.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.mahdi.androiderestaurant.login.LoginActivity
import fr.isen.mahdi.androiderestaurant.network.RegisterResult
import fr.isen.mahdi.androiderestaurant.network.User
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }
        binding.alreadyHaveAccountBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
        }
    }

    @SuppressLint("ShowToast")
    private fun register() {
        if(validateData()) {
            launchRequest()
        } else {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/register"
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
                binding.firstname.text?.isNotEmpty() == true &&
                binding.lastname.text?.isNotEmpty() == true &&
                binding.adresse.text?.isNotEmpty() == true &&
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
        postData.put("firstname", binding.firstname.text)
        postData.put("lastname", binding.lastname.text)
        postData.put("password", binding.password.text)
        postData.put("adress", binding.adresse.text)
        return postData
    }

    /**override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisterActivity.REQUEST_CODE) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val id_user = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if (id_user != -1) {
                sendOrder(id_user)
            }
        }
    }*/

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}