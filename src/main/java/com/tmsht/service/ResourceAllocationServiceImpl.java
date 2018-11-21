package com.tmsht.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmt.model.Employee;
import com.tmsht.dao.ResourceAllocationDao;
import com.tmsht.model.ResourceAllocation;
import com.tmsht.model.Task;

@Service(value = "resourceAllocationServiceImpl")
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

	@Autowired
	@Qualifier(value = "resourceAllocationDaoImpl")
	private ResourceAllocationDao resourceAllocationDao;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional
	public boolean createResourceAllocationByTask(List<Employee> employees, Task task, String notes) {
		LOGGER.debug("Creating resource-allocations for employeeIds {} under taskId {}", employees.toString(),
				task.getId());
		return resourceAllocationDao.createResourceAllocationByTask(employees, task, notes);
	}

	@Override
	@Transactional
	public boolean createResourceAllocationByResource(List<Task> tasks, Employee employee, String notes) {
		LOGGER.debug("Creating resource-allocations for taskIds {} under employeeId {}", tasks.toString(),
				employee.getId());
		return resourceAllocationDao.createResourceAllocationByResource(tasks, employee, notes);
	}

	@Override
	@Transactional
	public boolean removeResourceAllocationById(Set<Integer> resourceAllocationIds) {
		LOGGER.debug("Removing resource-allocation records: {}", resourceAllocationIds.toString());
		return resourceAllocationDao.removeResourceAllocationById(resourceAllocationIds);
	}

	@Override
	public List<ResourceAllocation> getResourceAllocationsByResourceId(int employeeId) {
		LOGGER.debug("Fetching resource-allocation records for employeeId {}", employeeId);
		return resourceAllocationDao.fetchResourceAllocationIdsByResourceId(employeeId);
	}

}
