package com.example.todo_list_application

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskItemRepository): ViewModel() {
    var taskItem: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()

    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {
        repository.insertTaskItem(newTask)
    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch {
        repository.updateTaskItem(taskItem)
    }

    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {
        if (!taskItem.isCompleted())
            taskItem.completedDateString = TaskItem.dateFormatter.format(LocalDate.now())
        repository.updateTaskItem(taskItem)
    }
}

class TaskItemModelFactory(private val repository: TaskItemRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw java.lang.IllegalArgumentException("Unknown Class for View Model")
    }
}