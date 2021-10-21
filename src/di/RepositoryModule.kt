package com.example.di

import com.example.features.auth.data.AuthRepository
import com.example.features.task.data.TaskRepository
import com.example.features.taskList.data.TaskListRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { TaskListRepository(get()) }
    single { TaskRepository(get()) }
}