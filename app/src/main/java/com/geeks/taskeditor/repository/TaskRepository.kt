package com.geeks.taskeditor.repository

import com.geeks.taskeditor.model.Task

class TaskRepository {
    private val taskList = mutableListOf<Task>()
    private var taskIdCounter = 0

    fun addTask(title: String) {
        val task = Task(taskIdCounter++, title, false)
        taskList.add(task)
    }

    fun updateTask(task: Task) {
        val existingTask = taskList.find { it.id == task.id }
        existingTask?.let {
            it.title = task.title
            it.isCompleted = task.isCompleted
        }
    }

    fun deleteTask(task: Task) {
        taskList.remove(task)
    }

    fun getAllTasks(): List<Task> {
        return taskList
    }
}
