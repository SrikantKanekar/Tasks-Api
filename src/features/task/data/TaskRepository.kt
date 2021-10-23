package com.example.features.task.data

import com.example.database.user.UserDataSource
import com.example.model.Task

class TaskRepository(
    private val userDataSource: UserDataSource,
) {
    suspend fun getTaskById(email: String, id: String, index: Int): Task? {
        val user = userDataSource.getUser(email)
        return user.taskLists[index].tasks.find { it.id == id }
    }

    suspend fun addTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].tasks.add(task)
        userDataSource.updateUser(user)
    }

    suspend fun updateTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
        val taskIndex = user.taskLists[index].tasks.indexOfFirst { it.id == task.id }
        user.taskLists[index].tasks[taskIndex] = task
        userDataSource.updateUser(user)
    }

    suspend fun toggleCompleted(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].tasks.find { it.id == task.id }?.apply { completed = !task.completed }
        userDataSource.updateUser(user)
    }

    suspend fun changeTaskList(email: String, task: Task, name: String, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].tasks.removeIf { it.id == task.id }
        user.taskLists.find { it.name == name }?.tasks?.add(task)
        userDataSource.updateUser(user)
    }

    suspend fun deleteTask(email: String, task: Task, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].tasks.removeIf { it.id == task.id }
        userDataSource.updateUser(user)
    }

    suspend fun deleteCompletedTasks(email: String, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].tasks.removeIf { it.completed }
        userDataSource.updateUser(user)
    }
}