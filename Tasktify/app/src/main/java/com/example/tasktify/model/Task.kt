package com.example.tasktify.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Locale

data class Task (
    var id: String? = "",
    val title: String,
    val description: String,
    val date: String? = "",
    var status: String? = "",
    var statusId: String? = ""

){
    fun getFormattedDate(): String{
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX", Locale.getDefault())
        val date = sdf.parse(date)

        val calendar = Calendar.getInstance()
        calendar.time = date

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val formattedDate = "${day.toString().padStart(2, '0')}/" +
                "${month.toString().padStart(2, '0')}/$year"


        return formattedDate
    }
}