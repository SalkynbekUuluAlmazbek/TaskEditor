package com.geeks.taskeditor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geeks.taskeditor.TaskViewModel
import com.geeks.taskeditor.databinding.ItemTaskBinding
import com.geeks.taskeditor.model.Task


class TaskAdapter(
    private val viewModel: TaskViewModel,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var tasks: List<Task> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.task = task
            binding.viewModel = viewModel

            binding.checkBoxTask.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
                viewModel.updateTask(task)
            }

            binding.root.setOnLongClickListener {
                viewModel.deleteTask(task)
                true
            }

            binding.root.setOnClickListener {
                // Вход в режим редактирования
                task.isEditing = true
                notifyDataSetChanged() // Обновление списка для отображения редактируемого элемента
            }

            if (task.isEditing) {
                // Показать поле для редактирования и установить обработчики для сохранения и отмены
                binding.viewTextTask.setText(task.title)

                binding.btnSaveEdit.setOnClickListener {
                    task.title = binding.viewTextTask.text.toString()
                    task.isEditing = false
                    viewModel.updateTask(task)
                    notifyDataSetChanged()
                }

                binding.btnCancelEdit.setOnClickListener {
                    task.isEditing = false
                    notifyDataSetChanged()
                }

            }
        }
    }
}