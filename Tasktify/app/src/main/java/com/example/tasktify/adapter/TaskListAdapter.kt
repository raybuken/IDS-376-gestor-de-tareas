package com.example.tasktify.adapter
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.BaseAdapter

import android.widget.TextView
import com.example.tasktify.R
import com.example.tasktify.model.Task

class TaskListAdapter(val ctx: Context, val taskDataList: List<Task>): BaseAdapter() {
    override fun getCount(): Int {
        return taskDataList.size
    }

    override fun getItem(position: Int): Task {
        return taskDataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val taskListConvertView: View
        val holder: ViewHolder

        if (convertView == null) {
            taskListConvertView = LayoutInflater.from(ctx).inflate(R.layout.task_list_item, parent, false)
            holder = ViewHolder()
            holder.titleItem = taskListConvertView.findViewById(R.id.task_list_item_title)
            holder.descriptionItem = taskListConvertView.findViewById(R.id.task_list_item_description)
            holder.idItem = taskListConvertView.findViewById(R.id.task_list_item_id)
            taskListConvertView.tag = holder
        } else {
            taskListConvertView = convertView
            holder = taskListConvertView.tag as ViewHolder
        }

        val currentItem = getItem(position)
        holder.titleItem?.text = currentItem.title
        holder.descriptionItem?.text = currentItem.description
        holder.idItem?.text = currentItem.id

        return taskListConvertView
    }

    private class ViewHolder (
        var titleItem: TextView? = null,
        var descriptionItem: TextView? = null,
        var idItem: TextView? = null
    )
}

