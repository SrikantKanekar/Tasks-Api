package com.example.features.taskList.routes

import com.example.features.taskList.data.TaskListRepository
import com.example.model.TaskList
import com.example.model.UserPrincipal
import com.example.util.constants.Auth.USER_AUTH
import com.example.util.enums.Order
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
        call.respond(HttpStatusCode.OK, taskLists)
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
    put("name/{index}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val name = call.receive<String>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.renameTaskList(principal.email, name, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.updateTaskListOrder(taskListRepository: TaskListRepository) {
    put("order/{index}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val order = call.receive<Order>()
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.updateTaskListOrder(principal.email, order, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteTaskList(taskListRepository: TaskListRepository) {
    delete("{id}") {
        val index = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val principal = call.principal<UserPrincipal>()!!
        taskListRepository.deleteTaskList(principal.email, index)
        call.respond(HttpStatusCode.OK)
    }
}