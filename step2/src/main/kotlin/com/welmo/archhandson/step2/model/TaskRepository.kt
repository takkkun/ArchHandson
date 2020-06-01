package com.welmo.archhandson.step2.model

import java.util.UUID

interface TaskRepository {
    fun list(): List<Task>

    fun get(id: UUID): Task

    fun create(task: Task)

    fun close(task: Task)
}
