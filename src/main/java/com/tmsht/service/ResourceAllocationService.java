package com.tmsht.service;

import java.util.List;
import java.util.Set;

import com.pmt.model.Employee;
import com.tmsht.model.ResourceAllocation;
import com.tmsht.model.Task;

public interface ResourceAllocationService {
	boolean createResourceAllocationByTask(List<Employee> employees, Task task, String notes);

	boolean createResourceAllocationByResource(List<Task> tasks, Employee employee, String notes);

	boolean removeResourceAllocationById(Set<Integer> resourceAllocationIds);

	List<ResourceAllocation> getResourceAllocationsByResourceId(int employeeId);

	List<ResourceAllocation> getResourceAllocationsByTaskId(int taskId);
}
