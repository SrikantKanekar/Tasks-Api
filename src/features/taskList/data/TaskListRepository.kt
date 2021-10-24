package com.example.features.taskList.data

import com.example.database.user.UserDataSource
import com.example.model.Notification
import com.example.model.TaskList
import com.example.util.enums.Order
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

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

    @ExperimentalTime
    suspend fun getNotifications(): List<Notification> {
        val users = userDataSource.getAllUser()
        val notifications = ArrayList<Notification>()

        users.forEach{ user ->
            user.taskLists.forEach { taskList ->
                val list = taskList.tasks
                    .filter { !it.completed }
                    .filter { it.dateTime != null }
                    .filter { task ->
                        val now = Clock.System.now()
                        val dateTime = Instant.parse(task.dateTime!!)
                        val difference = dateTime - now
                        val value = Duration.minutes(0) < difference && difference < Duration.minutes(1)
                        println("now $now")
                        println("dateTime $dateTime")
                        println("difference $difference")
                        println("value $value")
                        value
                    }
                    .map { Notification(it.name, user.token!!) }
                notifications.addAll(list)
            }
        }
        return notifications
    }
}