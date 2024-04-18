package com.example.tasktify.model

import java.text.SimpleDateFormat
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
        if(date == null){
            return ""
        }
        val parts = date.split('T')
        val datePart = parts[0].split('-')
        val year = datePart[0].toInt()
        val month = datePart[1].toInt() - 1
        val day = datePart[2].toInt()

        val timePart = parts[1].removeSuffix("Z").split(':')
        val hours = timePart[0].toInt()
        val minutes = timePart[1].toInt()
        val seconds = timePart[2].toInt()

        val calendar = Calendar.getInstance().apply {
            set(year, month, day, hours, minutes, seconds)
        }

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return dateFormat.format(calendar.time)
    }
}