package com.example.todo_list_application

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskItem = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItem.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItem.value
        list!!.add(newTask)
        taskItem.postValue(list)
    }

    fun updateTaskItem(id: UUID, name: String, desc: String, dueTime: LocalTime?){
        val list = taskItem.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = dueTime
        taskItem.postValue(list)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskValue: TaskItem){
        val list = taskItem.value
        val task = list!!.find { it.id == taskValue.id }!!
        if (task.completedDate == null)
            task.completedDate = LocalDate.now()
        taskItem.postValue(list)
    }
}