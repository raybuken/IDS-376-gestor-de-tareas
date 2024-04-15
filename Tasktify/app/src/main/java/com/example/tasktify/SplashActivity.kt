package com.example.tasktify

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktify.model.Token
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.Main) {
            try{
                val isLoggedIn = checkLoginStatus()
                if (isLoggedIn) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }

                finish()

            }catch(e: Exception){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
        }

    }

    private suspend fun checkLoginStatus(): Boolean {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        val taskService = RetrofitClient.taskService

        val call = withContext(Dispatchers.IO){
            taskService.validateToken(Token(id = token))
        }

        val editor = sharedPreferences.edit()

        editor.putString("token", call.value)
        editor.putBoolean("isLoggedIn", call.success)

        editor.apply()

        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    companion object {
        private const val SPLASH_DURATION = 2000L
    }
}