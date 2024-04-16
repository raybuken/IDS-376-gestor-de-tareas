package com.example.tasktify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktify.model.RegisterUser
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val context = this
        val registerButton = findViewById<Button>(R.id.button_register)
        val loginLink = findViewById<TextView>(R.id.link_to_login_anchor)


        registerButton.setOnClickListener(){
            val email = findViewById<EditText>(R.id.register_email_field).text.toString()
            val password = findViewById<EditText>(R.id.register_password_field).text.toString()
            val fullname = findViewById<EditText>(R.id.register_fullname_field).text.toString()

            val user = RegisterUser(
                email = email,
                password = password,
                fullName = fullname
            )

            val taskService = RetrofitClient.taskService

            CoroutineScope(Dispatchers.Main).launch{
                try{
                    registerButton.isEnabled = false
                    val call = withContext(Dispatchers.IO){
                        taskService.register(user)
                    }

                    startActivity(Intent(context, LoginActivity::class.java))

                    val toast = Toast.makeText(context, getString(R.string.user_register_success), Toast.LENGTH_LONG)
                    toast.show()

                }catch(e: Exception){
                    val a = e;
                    val toast = Toast.makeText(context, getString(R.string.system_error), Toast.LENGTH_LONG)
                    toast.show()
                }
                finally {
                    registerButton.isEnabled = true
                }
            }
        }

        loginLink.setOnClickListener(){
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}