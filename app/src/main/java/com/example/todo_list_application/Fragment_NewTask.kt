package com.example.todo_list_application

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.todo_list_application.databinding.FragmentNewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class Fragment_NewTask(var taskItem: TaskItem?) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentNewTaskBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskItem != null){
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if (taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        } else
            binding.taskTitle.text = "New Task"
        taskViewModel = ViewModelProvider(activity).get(TaskViewModel::class.java)
        binding.btnSave.setOnClickListener {
            saveAction()
        }
        binding.btnTimePicker.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val event = TimePickerDialog.OnTimeSetListener{
            _, selectedHour, selectedMin -> dueTime = LocalTime.of(selectedHour, selectedMin)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, event, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.btnTimePicker.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction(){
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
        if (taskItem == null){
            val newTask = TaskItem(name, desc, dueTimeString, null)
            taskViewModel.addTaskItem(newTask)
        } else{
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

}