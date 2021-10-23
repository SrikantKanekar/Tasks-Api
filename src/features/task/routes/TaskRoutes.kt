package com.example.features.task.routes

import com.example.features.task.data.TaskRepository
import com.example.model.Task
import com.example.model.UserPrincipal
import com.example.util.constants.Auth.USER_AUTH
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerTaskRoute() {

    val taskRepository by inject<TaskRepository>()

    routing {
        route("/tasks") {
            authenticate(USER_AUTH) {
                getTaskById(taskRepository)
                addTask(taskRepository)
                updateTask(taskRepository)
                toggleCompleted(taskRepository)
                changeTaskList(taskRepository)
                deleteTask(taskRepository)
                deleteCompletedTasks(taskRepository)
            }
        }
    }
}

fun Route.getTaskById(taskRepository: TaskRepository) {
    get("{index}/{id}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val id = call.parameters["id"] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val principal = call.principal<UserPrincipal>()!!
        val task = taskRepository.getTaskById(principal.email, id, index)
        when (task) {
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(HttpStatusCode.OK, task)
        }

    }
}

fun Route.addTask(taskRepository: TaskRepository) {
    post("{index}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@post call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val task = call.receive<Task>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.addTask(principal.email, task, index)
        call.respond(HttpStatusCode.Created)
    }
}

fun Route.updateTask(taskRepository: TaskRepository) {
    put("{index}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val task = call.receive<Task>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.updateTask(principal.email, task, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.toggleCompleted(taskRepository: TaskRepository) {
    put("toggle/{index}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val task = call.receive<Task>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.toggleCompleted(principal.email, task, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.changeTaskList(taskRepository: TaskRepository) {
    put("change/{index}/{name}") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val name = call.parameters["name"] ?: return@put call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val task = call.receive<Task>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.changeTaskList(principal.email, task, name, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteTask(taskRepository: TaskRepository) {
    delete("{index}/task") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val task = call.receive<Task>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.deleteTask(principal.email, task, index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteCompletedTasks(taskRepository: TaskRepository) {
    delete("{index}/completed") {
        val index = call.parameters["index"]?.toIntOrNull() ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing or malformed id"
        )
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.deleteCompletedTasks(principal.email, index)
        call.respond(HttpStatusCode.OK)
    }
}