package com.example.database

import com.example.model.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

const val DATABASE_NAME = "TASKS-DATABASE"
const val COLLECTION_USER = "USERS"

val mongoDbString = System.getenv("MONGODB_URI") ?: "mongodb://localhost"
private val client = KMongo.createClient(mongoDbString).coroutine
private val database = client.getDatabase(DATABASE_NAME)

val users = database.getCollection<User>(COLLECTION_USER)