package com.welmo.archhandson.step0.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.welmo.archhandson.step0.data.TaskTable
import com.welmo.archhandson.step0.model.Task
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.servlet.http.HttpServletRequest

@RestController
class TaskController(
    private val objectMapper: ObjectMapper
) {

    @GetMapping("/api/task/list")
    @Transactional(readOnly = true)
    fun list(request: HttpServletRequest): TaskList.Response {
        val query = TaskTable.selectAll()
        val tasks = query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }
        return TaskList.Response(
            tasks = tasks.map { task ->
                TaskList.Response.Item(
                    id = task.id,
                    title = task.title,
                    status = task.status
                )
            }
        )
    }

    @GetMapping("/api/task/get")
    @Transactional(readOnly = true)
    fun get(request: HttpServletRequest): TaskGet.Response {
        val id = UUID.fromString(request.getParameter("id"))
        val query = TaskTable.select { TaskTable.id eq id }
        val task = query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }.first()
        return TaskGet.Response(
            id = task.id,
            title = task.title,
            status = task.status
        )
    }

    @PostMapping("/api/task/create")
    @Transactional(readOnly = false)
    fun create(request: HttpServletRequest): TaskCreate.Response {
        val requestObject = objectMapper.readValue<TaskCreate.Request>(request.inputStream)
        val task = Task(
            id = UUID.randomUUID(),
            title = requestObject.title,
            status = "OPENED"
        )
        TaskTable.insert { row ->
            row[TaskTable.id] = task.id
            row[TaskTable.title] = task.title
            row[TaskTable.status] = task.status
        }
        return TaskCreate.Response(
            id = task.id
        )
    }

    @PostMapping("/api/task/close")
    @Transactional(readOnly = false)
    fun close(request: HttpServletRequest): TaskClose.Response {
        val requestObject = objectMapper.readValue<TaskClose.Request>(request.inputStream)
        val query = TaskTable.select { TaskTable.id eq requestObject.id }
        val task = query.map { row ->
            Task(
                id = row[TaskTable.id],
                title = row[TaskTable.title],
                status = row[TaskTable.status]
            )
        }.first()
        if (task.status != "OPENED") {
            throw RuntimeException("Task(${task.id}) is not opened. You can close only an opened task")
        }
        val closedTask = task.copy(status = "CLOSED")
        TaskTable.update({ TaskTable.id eq closedTask.id }) { row ->
            row[TaskTable.status] = closedTask.status
        }
        return TaskClose.Response(
            id = closedTask.id
        )
    }
}
