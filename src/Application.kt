package com.example

import com.example.di.productionModules
import com.example.setUp.*
import io.ktor.application.*
import org.koin.core.module.Module
import kotlin.time.ExperimentalTime

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@ExperimentalTime
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(
    testing: Boolean = false,
    koinModules: List<Module> = productionModules
) {
    koinSetup(koinModules)
    configSetup(testing)
    contentNegotiationSetup()
    firebaseSetup()
    authSetup()
    routesSetup()
}