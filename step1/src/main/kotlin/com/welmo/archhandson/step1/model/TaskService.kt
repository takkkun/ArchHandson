package com.welmo.archhandson.step1.model

import com.welmo.archhandson.step1.data.TaskTable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class TaskService {

    @Transactional(readOnly = true)
    fun list(): List<Task> {
        return TaskTable.list()
    }

    @Transactional(readOnly = true)
    fun get(id: UUID): Task {
        return TaskTable.get(id)
    }

    @Transactional(readOnly = false)
    fun create(title: String): UUID {
        val task = Task(
            id = UUID.randomUUID(),
            title = title,
            status = "OPENED"
        )
        TaskTable.create(task)
        return task.id
    }

    @Transactional(readOnly = false)
    fun close(id: UUID): UUID {
        val task = TaskTable.get(id)
        if (task.status != "OPENED") {
            throw RuntimeException("Task(${task.id}) is not opened. You can close only an opened task")
        }
        val closedTask = task.copy(status = "CLOSED")
        TaskTable.close(closedTask)
        return closedTask.id
    }
}
