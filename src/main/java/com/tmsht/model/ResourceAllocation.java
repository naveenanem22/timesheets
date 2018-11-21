package com.tmsht.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmt.model.Employee;

public class ResourceAllocation {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "task'")
	private Task task;

	@JsonProperty(value = "resource")
	private Employee resource;

	@JsonProperty(value = "resources")
	private List<Employee> resources;

	@JsonProperty(value = "tasks")
	private List<Task> tasks;

	@JsonProperty(value = "notes")
	private String notes;

	public ResourceAllocation() {

	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Employee getResource() {
		return resource;
	}

	public void setResource(Employee resource) {
		this.resource = resource;
	}

	public List<Employee> getResources() {
		return resources;
	}

	public void setResources(List<Employee> resources) {
		this.resources = resources;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public boolean isAllocationByTask() {
		return this.task != null;
	}

	public boolean isAllocationByResource() {
		return this.resource != null;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
