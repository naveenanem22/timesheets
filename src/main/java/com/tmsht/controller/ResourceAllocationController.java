package com.tmsht.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmsht.model.ResourceAllocation;
import com.tmsht.model.Task;
import com.tmsht.service.ResourceAllocationService;

@RestController(value = "resourceAllocationController")
@RequestMapping(path = "/tasks/{taskId}/resourceAllocations")
public class ResourceAllocationController {

	@Autowired
	@Qualifier(value = "resourceAllocationServiceImpl")
	private ResourceAllocationService resourceAllocationService;

	@Autowired
	@Qualifier(value = "taskAllocationController")
	private TaskAllocationController taskAllocationController;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResourceAllocation>> listResourceAllocationsByTask(@PathVariable("taskId") int taskId) {

		return new ResponseEntity<List<ResourceAllocation>>(
				resourceAllocationService.getResourceAllocationsByTaskId(taskId), HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> allocateResources(@Valid @RequestBody ResourceAllocation resourceAllocation,
			@PathVariable("taskId") int taskId) {
		Task task = new Task();
		task.setId(taskId);

		resourceAllocation.setTask(task);

		resourceAllocationService.createResourceAllocationByTask(resourceAllocation.getResources(),
				resourceAllocation.getTask(), resourceAllocation.getNotes());

		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deallocateTasks(@Valid @RequestBody Set<Integer> resourceAllocationIds) {
		return taskAllocationController.deallocateTasks(resourceAllocationIds);
	}

}
