package com.example.tasktify

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tasktify.adapter.TaskListAdapter
import com.example.tasktify.model.Task
import com.example.tasktify.network.RetrofitClient
import com.example.tasktify.network.RetrofitClient.taskService
import com.example.tasktify.ui.taskDetails.TaskDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        val context = this
        val id = intent.getStringExtra("ID")
        val sharedPreferences: SharedPreferences? = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val taskDetailsViewModel = ViewModelProvider(this).get(TaskDetailsViewModel::class.java)

        taskDetailsViewModel.task.observe(this) { task ->
            if(task != null){
                updateFields(task)
            }
        }

        if (id != null) {
            taskDetailsViewModel.getTaskDetails(sharedPreferences = sharedPreferences, taskId = id)
        }

        val saveTaskButton = findViewById<Button>(R.id.update_task_button)
        val deleteTaskButton = findViewById<TextView>(R.id.delete_task_button)
        val errorMessageText = findViewById<TextView>(R.id.create_task_error_message)

        saveTaskButton.setOnClickListener(){
            val taskService = RetrofitClient.taskService
            val taskTitle = findViewById<EditText>(R.id.edit_text_title)
            val taskDescription = findViewById<EditText>(R.id.edit_text_description)
            val token = sharedPreferences?.getString("token", "")
            val title = taskTitle.text.toString()
            val description = taskDescription.text.toString()
            val task = Task(
                id = id,
                title = title,
                description = description
            )

            errorMessageText.text = ""

            if(title.isEmpty()){
                errorMessageText.text = getString(R.string.title_required)
                return@setOnClickListener
            }
            if(description.isEmpty()){
                errorMessageText.text = getString(R.string.description_required)
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.Main).launch {
                setButtonsStatus(false)
                try{
                    val call = CoroutineScope(Dispatchers.Main).async{
                        taskService.updateTask(token = token, task = task, taskId = task.id)
                    }

                    val results = call.await()

                    updateFields(results)

                    val toast = Toast.makeText(context, "Task updated successful!", Toast.LENGTH_SHORT)
                    toast.show()
                }catch(e: Exception){

                }finally {
                    setButtonsStatus(true)
                }
            }

        }

        deleteTaskButton.setOnClickListener(){
            val token = sharedPreferences?.getString("token", "")

            CoroutineScope(Dispatchers.Main).launch {
                setButtonsStatus(false)
                try{
                    val call = CoroutineScope(Dispatchers.Main).async{
                        taskService.deleteTask(token = token, taskId = id)
                    }

                    val results = call.await()

                    if(results.success){
                        val toast = Toast.makeText(context, "Task deleted!", Toast.LENGTH_SHORT)
                        toast.show()

                        startActivity(Intent(context, MainActivity::class.java))

                        finish()
                    }

                }catch(e: Exception){
                    errorMessageText.text = getString(R.string.system_error)
                }finally {
                    setButtonsStatus(true)
                }
            }
        }
    }
    private fun updateFields(task: Task){
        val taskTitle = findViewById<EditText>(R.id.edit_text_title)
        val taskDescription = findViewById<EditText>(R.id.edit_text_description)

        taskTitle.setText(task.title)
        taskDescription.setText(task.description)
    }

    private fun setButtonsStatus(enabled: Boolean){
        val saveTaskButton = findViewById<Button>(R.id.update_task_button)
        val deleteTaskButton = findViewById<TextView>(R.id.delete_task_button)

        saveTaskButton.isEnabled = enabled
        deleteTaskButton.isEnabled = enabled

    }

}