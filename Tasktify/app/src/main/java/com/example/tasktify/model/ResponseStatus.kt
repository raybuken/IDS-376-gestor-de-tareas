package com.example.tasktify.model

import com.google.gson.annotations.SerializedName

data class ResponseStatus (
    val message: String?,
    val success: Boolean,
    val value: String?
)