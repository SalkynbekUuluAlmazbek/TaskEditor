package com.geeks.taskeditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.taskeditor.adapter.TaskAdapter
import com.geeks.taskeditor.databinding.ActivityMainBinding
import com.geeks.taskeditor.model.FilterType
import com.geeks.taskeditor.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        binding.viewModel = viewModel

        taskAdapter = TaskAdapter(viewModel)
        binding.recycleView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.addTaskButton.setOnClickListener {
            val taskTitle = binding.editTextTask.text.toString()
            if (taskTitle.isNotEmpty()) {
                viewModel.addTask(taskTitle)
                binding.editTextTask.text.clear()
            }
        }


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioAll -> viewModel.setFilterType(FilterType.ALL)
                R.id.radioCompleted -> viewModel.setFilterType(FilterType.COMPLETED)
                R.id.radioIncomplete -> viewModel.setFilterType(FilterType.INCOMPLETE)
            }
        }

        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.setFilterType(FilterType.ALL)
                    1 -> viewModel.setFilterType(FilterType.COMPLETED)
                    2 -> viewModel.setFilterType(FilterType.INCOMPLETE)
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.filteredTasks.observe(this, { tasks -> // Changed to filteredTasks
            taskAdapter.tasks = tasks
            taskAdapter.notifyDataSetChanged()
        })
    }
}