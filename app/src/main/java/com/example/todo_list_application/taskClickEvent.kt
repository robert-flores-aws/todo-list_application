package com.example.todo_list_application

interface taskClickEvent {
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}