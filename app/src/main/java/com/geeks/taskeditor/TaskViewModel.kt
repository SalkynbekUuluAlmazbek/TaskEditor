package com.geeks.taskeditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.geeks.taskeditor.model.FilterType
import com.geeks.taskeditor.model.Task
import com.geeks.taskeditor.repository.TaskRepository

class TaskViewModel : ViewModel() {
    private val repository = TaskRepository()

    private val _filterType = MutableLiveData(FilterType.ALL)
    val filterType: LiveData<FilterType>
        get() = _filterType

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() = _tasks

    val filteredTasks: LiveData<List<Task>> = _tasks.map { tasks ->
        when (_filterType.value) {
            FilterType.COMPLETED -> tasks.filter { it.isCompleted }
            FilterType.INCOMPLETE -> tasks.filter { !it.isCompleted }
            else -> tasks
        }
    }

    init {
        _tasks.value = repository.getAllTasks()
    }

    fun addTask(title: String) {
        repository.addTask(title)
        _tasks.value = repository.getAllTasks()
    }

    fun updateTask(task: Task) {
        repository.updateTask(task)
        _tasks.value = repository.getAllTasks()
    }

    fun deleteTask(task: Task) {
        repository.deleteTask(task)
        _tasks.value = repository.getAllTasks()
    }

    fun setFilterType(filterType: FilterType) {
        _filterType.postValue(filterType)
    }
}