package com.example.features.task.routes

import com.example.features.task.data.TaskRepository
import com.example.features.task.requests.*
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
    get {
        val body = call.receive<GetTaskRequest>()
        val principal = call.principal<UserPrincipal>()!!
        val task = taskRepository.getTaskById(principal.email, body.id, body.index)
        when(task){
            null -> call.respond(HttpStatusCode.NotFound)
            else -> call.respond(HttpStatusCode.OK, task)
        }

    }
}

fun Route.addTask(taskRepository: TaskRepository) {
    post {
        val body = call.receive<TaskRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.addTask(principal.email, body.task, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.updateTask(taskRepository: TaskRepository) {
    put {
        val body = call.receive<TaskRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.updateTask(principal.email, body.task, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.toggleCompleted(taskRepository: TaskRepository) {
    put {
        val body = call.receive<TaskRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.toggleCompleted(principal.email, body.task, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.changeTaskList(taskRepository: TaskRepository) {
    delete {
        val body = call.receive<ChangeTaskListRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.changeTaskList(principal.email, body.task, body.name, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteTask(taskRepository: TaskRepository) {
    delete {
        val body = call.receive<TaskRequest>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.deleteTask(principal.email, body.task, body.index)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.deleteCompletedTasks(taskRepository: TaskRepository) {
    delete {
        val index = call.receive<Int>()
        val principal = call.principal<UserPrincipal>()!!
        taskRepository.deleteCompletedTasks(principal.email, index)
        call.respond(HttpStatusCode.OK)
    }
}