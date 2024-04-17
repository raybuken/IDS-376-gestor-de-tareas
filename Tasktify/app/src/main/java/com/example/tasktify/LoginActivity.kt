package com.example.tasktify

import android.content.Context
import android.content.Intent
import retrofit2.HttpException
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktify.model.Login
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity: AppCompatActivity() {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val context = this

        val loginButton = findViewById<Button>(R.id.button_login)
        val registerLink = findViewById<TextView>(R.id.link_to_register_anchor)
        val errorMessageText = findViewById<TextView>(R.id.login_error_message)

        loginButton.setOnClickListener{
            val email = findViewById<TextView>(R.id.login_email_field).text.toString()
            val password = findViewById<TextView>(R.id.login_password_field).text.toString()

            val taskService = RetrofitClient.taskService

            if(email.isEmpty()){
                errorMessageText.text = getString(R.string.email_required)
                return@setOnClickListener
            }

            if(password.isEmpty()){
                errorMessageText.text = getString(R.string.password_required)
                return@setOnClickListener
            }

            errorMessageText.text = ""

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

                    finish()

                }catch(e: Exception){
                    if(e is HttpException){
                        val statusCode = e.code()
                        if(statusCode == 401 || statusCode == 404 ){
                            errorMessageText.text = getString(R.string.email_password_mismatch_error)
                        }else{
                            errorMessageText.text = getString(R.string.system_error)
                        }
                    }else{
                        errorMessageText.text = getString(R.string.system_error)
                    }
                }
                finally {
                    loginButton.isEnabled = true
                }
            }
        }

        registerLink.setOnClickListener(){
            startActivity(Intent(context, RegisterActivity::class.java))
            finish()
        }

    }
}