package com.welmo.archhandson.step1.data

import com.welmo.archhandson.step1.model.Task
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.UUID

object TaskTable : Table("task") {
    val id = uuid("id")

    val title = varchar("title", 50)

    val status = varchar("status", 10)

    override val primaryKey = PrimaryKey(id)

    fun list(): List<Task> {
        val query = selectAll()
        return query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }
    }

    fun get(id: UUID): Task {
        val query = select { TaskTable.id eq id }
        return query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }.first()
    }

    fun create(task: Task) {
        insert { row ->
            row[TaskTable.id] = task.id
            row[TaskTable.title] = task.title
            row[TaskTable.status] = task.status
        }
    }

    fun close(task: Task) {
        update({ TaskTable.id eq task.id }) { row ->
            row[TaskTable.status] = task.status
        }
    }
}
