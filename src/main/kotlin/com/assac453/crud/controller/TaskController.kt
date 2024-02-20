package com.assac453.crud.controller

import com.assac453.crud.dto.TaskDTORequest
import com.assac453.crud.dto.TaskDTOResponse
import com.assac453.crud.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/tasks")
class TaskController(var service: TaskService) {
    @PostMapping("/new")
    fun createTask(@RequestBody newTask: TaskDTORequest): TaskDTOResponse {
        return service.createTask(newTask)
    }
    @GetMapping("/{id}")
    fun getById(@PathVariable(name = "id") id: Long) : TaskDTOResponse {
        return service.getById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }
    @PutMapping("/{id}")
    fun updateTask(@PathVariable(name = "id") id: Long, @RequestBody old: TaskDTORequest) : TaskDTOResponse{
        return service.updateTask(id, old)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
    }
    @DeleteMapping("/{id}")
    fun delete(@PathVariable(name = "id") id: Long) : Boolean{
        return service.delete(id)
    }

    @GetMapping("")
    fun getAll() : List<TaskDTOResponse>{
        return service.getAll()
    }
}