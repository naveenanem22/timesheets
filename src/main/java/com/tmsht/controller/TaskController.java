package com.tmsht.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsht.model.Task;
import com.tmsht.service.TaskService;

@RestController("taskController")
@RequestMapping("/v0/task-management/tasks")
@Validated
public class TaskController {

	@Autowired
	@Qualifier(value = "taskServiceImpl")
	private TaskService taskService;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Task>> listAllTasksByProjectId() {
		return new ResponseEntity<List<Task>>(taskService.listAllTasks(), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createTasksByProjectId(@RequestBody List<Task> tasks) {
		taskService.createTasks(tasks);
		return ResponseEntity.noContent().build();

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTasksByProjectId(@RequestBody List<Task> tasks) {
		taskService.updateTasks(tasks);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteTasksByTaskId(@Valid @RequestBody Set<Integer> taskIds) {
		taskService.deleteTasksById(taskIds);
		return ResponseEntity.noContent().build();

	}

}
