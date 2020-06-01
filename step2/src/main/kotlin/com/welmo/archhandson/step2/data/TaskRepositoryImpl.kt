package com.welmo.archhandson.step2.data

import com.welmo.archhandson.step2.model.Task
import com.welmo.archhandson.step2.model.TaskRepository
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TaskRepositoryImpl : TaskRepository {
    override fun list(): List<Task> {
        val query = TaskTable.selectAll()
        return query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }
    }

    override fun get(id: UUID): Task {
        val query = TaskTable.select { TaskTable.id eq id }
        return query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }.first()
    }

    override fun create(task: Task) {
        TaskTable.insert { row ->
            row[TaskTable.id] = task.id
            row[TaskTable.title] = task.title
            row[TaskTable.status] = task.status
        }
    }

    override fun close(task: Task) {
        TaskTable.update({ TaskTable.id eq task.id }) { row ->
            row[TaskTable.status] = task.status
        }
    }
}
