package com.example.tasktify.model

data class User (
    val id: String,
    val fullname: String,
    var password: String?,
    val email: String
)