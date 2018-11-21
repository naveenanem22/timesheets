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

import com.pmt.model.Employee;
import com.tmsht.model.ResourceAllocation;
import com.tmsht.service.ResourceAllocationService;

@RestController
@RequestMapping(path = "/employees/{employeeId}/resourceAllocations")
public class TaskAllocationController {

	@Autowired
	@Qualifier(value = "resourceAllocationServiceImpl")
	private ResourceAllocationService resourceAllocationService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> allocateTask(@Valid @RequestBody ResourceAllocation resourceAllocation,
			@PathVariable("employeeId") int employeeId) {
		Employee employee = new Employee();
		employee.setId(employeeId);

		resourceAllocation.setResource(employee);

		resourceAllocationService.createResourceAllocationByResource(resourceAllocation.getTasks(),
				resourceAllocation.getResource(), resourceAllocation.getNotes());

		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deallocateTask(@Valid @RequestBody Set<Integer> resourceAllocationIds) {
		resourceAllocationService.removeResourceAllocationById(resourceAllocationIds);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ResourceAllocation>> fetchResourceAllocations(
			@PathVariable("employeeId") int employeeId) {

		return new ResponseEntity<List<ResourceAllocation>>(
				resourceAllocationService.getResourceAllocationsByResourceId(employeeId), HttpStatus.ACCEPTED);

	}

}
