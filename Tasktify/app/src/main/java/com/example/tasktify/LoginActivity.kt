package com.example.tasktify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktify.model.Login
import com.example.tasktify.model.Token
import com.example.tasktify.network.RetrofitClient
import com.example.tasktify.network.RetrofitClient.taskService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val context = this

        val loginButton = findViewById<Button>(R.id.button_login)
        val registerLink = findViewById<TextView>(R.id.link_to_register_anchor)

        loginButton.setOnClickListener{
            val email = findViewById<TextView>(R.id.login_email_field).text.toString()
            val password = findViewById<TextView>(R.id.login_password_field).text.toString()

            val taskService = RetrofitClient.taskService

            CoroutineScope(Dispatchers.Main).launch{
                try{
                    loginButton.isEnabled = false
                    val call = withContext(Dispatchers.IO){
                        taskService.authenticate(Login(email, password))
                    }

                    val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()

                    editor.putString("token", call.value)
                    editor.putBoolean("isLoggedIn", call.success)

                    editor.apply()

                    startActivity(Intent(context, MainActivity::class.java))

                }catch(e: Exception){
                    val toast = Toast.makeText(context, getString(R.string.system_error), Toast.LENGTH_LONG)
                    toast.show()
                }
                finally {
                    loginButton.isEnabled = true
                }

            }

        }

        registerLink.setOnClickListener(){
            startActivity(Intent(context, RegisterActivity::class.java))
        }


    }
}