package com.example.tasktify.ui.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tasktify.R
import android.widget.DatePicker
import java.util.Calendar
import java.util.Date

class DatePickerFragment: DialogFragment() {
    private var onDateSelectedListener: ((Date) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){
        val calendar = Calendar.getInstance()
        val calendarYear = calendar.get(Calendar.YEAR)
        val calendarMonth = calendar.get(Calendar.MONTH)
        val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = view?.findViewById<DatePicker>(R.id.datePicker)

        datePicker!!.init(calendarYear, calendarMonth, calendarDay) { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            onDateSelectedListener?.invoke(selectedDate.time)
            dismiss()
        }
    }

    fun setOnDateSelectedListener(listener: (Date) -> Unit) {
        this.onDateSelectedListener = listener
    }
}