package com.example.features.taskList.data

import com.example.database.user.UserDataSource
import com.example.model.TaskList
import com.example.util.enums.Order

class TaskListRepository(
    private val userDataSource: UserDataSource,
) {
    suspend fun getTaskLists(email: String): List<TaskList> {
        val user = userDataSource.getUser(email)
        return user.taskLists
    }

    suspend fun addTaskList(email: String, taskList: TaskList) {
        val user = userDataSource.getUser(email)
        user.taskLists.add(taskList)
        userDataSource.updateUser(user)
    }

    suspend fun renameTaskList(email: String, name: String, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].name = name
        userDataSource.updateUser(user)
    }

    suspend fun updateTaskListOrder(email: String, order: Order, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists[index].order = order
        userDataSource.updateUser(user)
    }

    suspend fun deleteTaskList(email: String, index: Int) {
        val user = userDataSource.getUser(email)
        user.taskLists.removeAt(index)
        userDataSource.updateUser(user)
    }
}