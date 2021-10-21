package com.example.features.taskList.routes

import com.example.features.taskList.data.TaskListRepository
import com.example.features.taskList.requests.OrderChangeRequest
import com.example.features.taskList.requests.RenameRequest
import com.example.model.TaskList
import com.example.model.UserPrincipal
import com.example.util.constants.Auth
import com.example.util.constants.Auth.USER_AUTH
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerTaskListRoute() {

    val taskListRepository by inject<TaskListRepository>()

    routing {
        route("/task-lists") {
            authenticate(USER_AUTH) {
                getTaskLists(taskListRepository)
                addTaskList(taskListRepository)
                renameTaskList(taskListRepository)
                updateTaskListOrder(taskListRepository)
                deleteTaskList(taskListRepository)
            }
        }
    }
}

fun Route.getTaskLists(taskListRepository: TaskListRepository) {
    get {
        val principal = call.principal<UserPrincipal>()!!
        val taskLists = taskListRepository.getTaskLists(principal.email)
        call.respond(HttpStatusCode.Created, taskLists)
    }
}

fun Route.addTaskList(taskListRepository: TaskListRepository) {
    post {
        val taskList = call.receive<TaskList>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.addTaskList(principal.email, taskList)
        call.respond(HttpStatusCode.Created)
    }
}

fun Route.renameTaskList(taskListRepository: TaskListRepository) {
    put {
        val body = call.receive<RenameRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.renameTaskList(principal.email, body.name, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.updateTaskListOrder(taskListRepository: TaskListRepository) {
    put {
        val body = call.receive<OrderChangeRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.updateTaskListOrder(principal.email, body.order, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteTaskList(taskListRepository: TaskListRepository) {
    delete {
        val index = call.receive<Int>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.deleteTaskList(principal.email, index)
        call.respond(HttpStatusCode.OK)
    }
}