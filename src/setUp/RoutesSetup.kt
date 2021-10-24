package com.example.setUp

import com.example.features.auth.routes.registerAuthRoutes
import com.example.features.task.routes.registerTaskRoute
import com.example.features.taskList.routes.registerTaskListRoute
import com.example.features.util.notificationRoute
import com.example.features.util.registerStatusRoutes
import io.ktor.application.*

fun Application.routesSetup() {
    registerAuthRoutes()
    registerTaskListRoute()
    registerTaskRoute()
    registerStatusRoutes()
    notificationRoute()
}