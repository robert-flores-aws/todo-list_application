package com.example.todo_list_application

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list_application.databinding.TaskViewlistBinding

class taskAdapter(
    private val taskItems: List<TaskItem>,
    private val clickEvent: taskClickEvent
): RecyclerView.Adapter<taskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): taskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskViewlistBinding.inflate(from, parent, false)
        return taskViewHolder(parent.context, binding, clickEvent)
    }

    override fun onBindViewHolder(holder: taskViewHolder, position: Int) {
        holder.bindTaskItem(taskItems[position])
    }

    override fun getItemCount(): Int = taskItems.size
}