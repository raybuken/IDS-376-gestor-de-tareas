package com.example.tasktify.model

data class Task (
    var id: String? = "",
    val title: String,
    val description: String,
    val date: String?,
    var status: String? = "",
    var statusId: String? = ""
)