package com.example.todo_list_application

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_list_application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), taskClickEvent {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNewTask.setOnClickListener {
            Fragment_NewTask(null).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItem.observe(this){
            binding.rvList.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = taskAdapter(it, mainActivity)
            }
        }

    }

    override fun editTaskItem(taskItem: TaskItem) {
        Fragment_NewTask(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}