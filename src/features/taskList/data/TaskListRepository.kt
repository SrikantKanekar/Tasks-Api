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

    suspend fun addTaskList(email: String, taskList:TaskList){
        val user = userDataSource.getUser(email)
    }

    suspend fun renameTaskList(email: String, name: String, index: Int){
        val user = userDataSource.getUser(email)
    }

    suspend fun updateTaskListOrder(email: String, order: Order, index: Int){
        val user = userDataSource.getUser(email)
    }

    suspend fun deleteTaskList(email: String, index: Int){
        val user = userDataSource.getUser(email)
    }
}