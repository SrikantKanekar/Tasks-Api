package com.example

import com.example.setUp.configSetup
import com.example.setUp.koinSetup
import com.example.di.productionModules
import com.example.setUp.authSetup
import com.example.setUp.contentNegotiationSetup
import com.example.setUp.routesSetup
import io.ktor.application.*
import org.koin.core.module.Module

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(
    testing: Boolean = false,
    koinModules: List<Module> = productionModules
) {
    koinSetup(koinModules)
    configSetup(testing)
    contentNegotiationSetup()
    authSetup()
    routesSetup()
}