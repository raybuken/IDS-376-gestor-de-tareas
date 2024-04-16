package com.example.tasktify

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktify.model.Task
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTaskActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val context = this

        val saveTaskButton = findViewById<Button>(R.id.save_task_button)

        saveTaskButton.setOnClickListener(){
            val title = findViewById<EditText>(R.id.edit_text_title).text.toString()
            val description = findViewById<EditText>(R.id.edit_text_description).text.toString()

            val taskService = RetrofitClient.taskService
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

            CoroutineScope(Dispatchers.Main).launch{
                try{
                    saveTaskButton.isEnabled = false
                    val task = Task(
                        title = title,
                        description = description,
                        id = "",
                        status = "",
                        statusId = "",
                    )
                    val results = withContext(Dispatchers.IO){
                        taskService.createTask(
                           token = sharedPreferences.getString("token", ""),
                            task = task
                        )
                    }

                    if(results.success){
                        val message: String = getString(R.string.successful_new_task)
                        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                        toast.show()
                        startActivity(Intent(context, MainActivity::class.java))

                        finish()
                    }else{

                    }

                }catch(e: Exception){
                    val a = e;
                }
                finally {
                    saveTaskButton.isEnabled = true
                }

            }
        }
    }
}