package com.example.tasktify.model

data class TaskListStatus(
    val success: Boolean = false,
    val items: List<Task>
)