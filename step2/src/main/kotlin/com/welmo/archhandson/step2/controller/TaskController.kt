package com.welmo.archhandson.step2.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.welmo.archhandson.step2.model.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.servlet.http.HttpServletRequest

@RestController
class TaskController(
    private val taskService: TaskService,
    private val objectMapper: ObjectMapper
) {

    @GetMapping("/api/task/list")
    fun list(request: HttpServletRequest): TaskList.Response {
        val tasks = taskService.list()
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
    fun get(request: HttpServletRequest): TaskGet.Response {
        val id = UUID.fromString(request.getParameter("id"))
        val task = taskService.get(id)
        return TaskGet.Response(
            id = task.id,
            title = task.title,
            status = task.status
        )
    }

    @PostMapping("/api/task/create")
    fun create(request: HttpServletRequest): TaskCreate.Response {
        val requestObject = objectMapper.readValue<TaskCreate.Request>(request.inputStream)
        val id = taskService.create(requestObject.title)
        return TaskCreate.Response(
            id = id
        )
    }

    @PostMapping("/api/task/close")
    fun close(request: HttpServletRequest): TaskClose.Response {
        val requestObject = objectMapper.readValue<TaskClose.Request>(request.inputStream)
        val closedId = taskService.close(requestObject.id)
        return TaskClose.Response(
            id = closedId
        )
    }
}
