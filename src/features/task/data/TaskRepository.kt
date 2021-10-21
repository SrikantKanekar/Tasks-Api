package com.example.features.task.data

import com.example.database.user.UserDataSource
import com.example.model.Task
import com.example.model.TaskList
import com.example.util.enums.Order

class TaskRepository(
    private val userDataSource: UserDataSource,
) {
    suspend fun getTaskById(email: String, id: String, index: Int): Task? {
        val user = userDataSource.getUser(email)
        return user.taskLists[index].tasks.find { it.id == id }
    }

    suspend fun addTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
    }

    suspend fun updateTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
    }

    suspend fun toggleCompleted(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
    }

    suspend fun changeTaskList(email: String, task: Task, name: String, index: Int) {
        val user = userDataSource.getUser(email)
    }

    suspend fun deleteTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
    }

    suspend fun deleteCompletedTasks(email: String, index: Int) {
        val user = userDataSource.getUser(email)
    }
}