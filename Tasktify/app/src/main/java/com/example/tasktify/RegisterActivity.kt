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
        val errorMessageText = findViewById<TextView>(R.id.register_error_message)

        registerButton.setOnClickListener(){
            val email = findViewById<EditText>(R.id.register_email_field).text.toString()
            val password = findViewById<EditText>(R.id.register_password_field).text.toString()
            val fullname = findViewById<EditText>(R.id.register_fullname_field).text.toString()

            if(email.isEmpty()){
                errorMessageText.text = getString(R.string.email_required)
                return@setOnClickListener
            }

            if(password.isEmpty()){
                errorMessageText.text = getString(R.string.password_required)
                return@setOnClickListener
            }

            if(fullname.isEmpty()){
                errorMessageText.text = getString(R.string.fullname_required)
                return@setOnClickListener
            }

            val user = RegisterUser(
                email = email,
                password = password,
                fullName = fullname
            )

            val taskService = RetrofitClient.taskService

            errorMessageText.text = ""

            CoroutineScope(Dispatchers.Main).launch{
                try{
                    registerButton.isEnabled = false
                    val call = withContext(Dispatchers.IO){
                        taskService.register(user)
                    }

                    startActivity(Intent(context, LoginActivity::class.java))

                    val toast = Toast.makeText(context, getString(R.string.user_register_success), Toast.LENGTH_LONG)
                    toast.show()

                    finish()

                }catch(e: Exception){
                    errorMessageText.text = getString(R.string.system_error)
                }
                finally {
                    registerButton.isEnabled = true
                }
            }
        }

        loginLink.setOnClickListener(){
            startActivity(Intent(context, LoginActivity::class.java))

            finish()
        }
    }
}