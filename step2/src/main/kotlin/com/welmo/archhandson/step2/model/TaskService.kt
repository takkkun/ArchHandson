package com.welmo.archhandson.step2.model

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    @Transactional(readOnly = true)
    fun list(): List<Task> {
        return taskRepository.list()
    }

    @Transactional(readOnly = true)
    fun get(id: UUID): Task {
        return taskRepository.get(id)
    }

    @Transactional(readOnly = false)
    fun create(title: String): UUID {
        val task = Task(
            id = UUID.randomUUID(),
            title = title,
            status = "OPENED"
        )
        taskRepository.create(task)
        return task.id
    }

    @Transactional(readOnly = false)
    fun close(id: UUID): UUID {
        val task = taskRepository.get(id)
        if (task.status != "OPENED") {
            throw RuntimeException("Task(${task.id}) is not opened. You can close only an opened task")
        }
        val closedTask = task.copy(status = "CLOSED")
        taskRepository.close(closedTask)
        return closedTask.id
    }
}
