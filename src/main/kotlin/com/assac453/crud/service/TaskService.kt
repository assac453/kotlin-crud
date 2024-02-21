package com.assac453.crud.service

import com.assac453.crud.dto.TaskDTORequest
import com.assac453.crud.dto.TaskDTOResponse
import com.assac453.crud.entity.TaskEntity
import com.assac453.crud.repository.TaskRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class TaskService(var repository: TaskRepository) {
    fun createTask(newTask: TaskDTORequest): TaskDTOResponse {
        val save =
            repository.save(TaskEntity(null, newTask.name, newTask.description, newTask.done))
        return TaskDTOResponse(save.id!!, save.name, save.description, save.done)
    }
    fun getById(id: Long): TaskDTOResponse? {
        return repository
            .findById(id)
            .map { TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done)}
            .getOrNull()
    }
    fun updateTask(id: Long, new: TaskDTORequest): TaskDTOResponse? {
        return repository
            .findById(id)
            .map { val save =
                repository.save(TaskEntity(id = it.id!!, name = new.name, description = new.description, done = new.done))
                TaskDTOResponse(id = save.id!!, name = save.name, description = save.description, done = save.done)
            }.orElseGet(null)
    }
    fun delete(id: Long): Boolean {
        repository.deleteById(id)
        return !repository.existsById(id)
    }

    fun getAll(): List<TaskDTOResponse> {
        return repository
            .findAll()
            .map { TaskDTOResponse(id = it.id!!, name = it.name, description = it.description, done = it.done) }
    }
}
