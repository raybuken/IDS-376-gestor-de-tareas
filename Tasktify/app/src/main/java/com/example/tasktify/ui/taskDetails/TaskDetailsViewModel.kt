package com.example.tasktify.ui.taskDetails

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktify.model.Task
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskDetailsViewModel(): ViewModel() {
    private val taskService = RetrofitClient.taskService

    private val _task = MutableLiveData<Task?>()
    val task: MutableLiveData<Task?> = _task

    fun getTaskDetails(sharedPreferences: SharedPreferences?, taskId : String = ""){
        viewModelScope.launch{
            try{
                val token = sharedPreferences?.getString("token", "")
                val results = withContext(Dispatchers.Main){
                    taskService.getTask(token = token, taskId = taskId )
                }

                _task.postValue(results)

            }catch(e: Exception){
                _task.postValue(null)
            }
        }
    }
}