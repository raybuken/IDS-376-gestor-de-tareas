package com.example.tasktify.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tasktify.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.lifecycle.viewModelScope
import com.example.tasktify.model.Task
import com.example.tasktify.model.TaskListStatus

import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val taskService = RetrofitClient.taskService

    private val _taskList = MutableLiveData<List<Task>?>()
    val taskList: MutableLiveData<List<Task>?> = _taskList

    fun loadTask(sharedPreferences: SharedPreferences?){
        viewModelScope.launch{
            try{
                val token = sharedPreferences?.getString("token", "")
                val results = withContext(Dispatchers.Main){
                    taskService.getTaskList(token)
                }

                _taskList.postValue(results.items)

            }catch(e: Exception){
                _taskList.postValue(null)
            }
        }
    }

}