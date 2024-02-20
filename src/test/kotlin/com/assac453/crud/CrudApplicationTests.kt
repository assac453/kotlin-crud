package com.assac453.crud

import com.assac453.crud.dto.TaskDTORequest
import com.assac453.crud.dto.TaskDTOResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
	classes = [CrudApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CrudApplicationTests(@Autowired var restTemplate: TestRestTemplate) {

	@Test
	fun createTaskShouldReturnTrue() {



		var taskDTORequest = TaskDTORequest(name = "Task", description = "Description", done = false)
		var result = this.restTemplate.postForEntity("/tasks/create", taskDTORequest, TaskDTOResponse::class.java)
		val taskId = result.body?.id!!
		assertTrue { result.body?.name.equals("Task") }
		assertTrue { result.body?.description.equals("Description") }



		result = this.restTemplate.getForEntity("/tasks/{id}", TaskDTOResponse::class.java, taskId)
		assertTrue{ result.body?.name.equals("Task") }



		taskDTORequest = TaskDTORequest(name = taskDTORequest.name, description = taskDTORequest.description, done = !taskDTORequest.done)
		this.restTemplate.put("/tasks/{id}", taskDTORequest, taskId)
		result = this.restTemplate.getForEntity("/tasks/{id}", TaskDTOResponse::class.java ,taskId)
		assertTrue{ result.statusCode.is2xxSuccessful}
		assertTrue{ result.body?.done!! }



		this.restTemplate.delete("/tasks/{id}", taskId)
		val result1 = this.restTemplate.getForEntity("/tasks/{id}", String::class.java, taskId)
		assertTrue{ result1.statusCode == HttpStatus.NOT_FOUND }

	}


}
