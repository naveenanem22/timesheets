package com.tmsht.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pmt.model.Employee;
import com.tmsht.custom.serializer.EmployeeInResourceAllocationSerializer;
import com.tmsht.custom.serializer.ProjectInResourceAllocationSerializer;
import com.tmsht.custom.serializer.TaskInResourceAllocationSerializer;

public class ResourceAllocation {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "task'")
	@JsonSerialize(using = TaskInResourceAllocationSerializer.class)
	private Task task;

	@JsonProperty(value = "resource")
	@JsonSerialize(using = EmployeeInResourceAllocationSerializer.class)
	private Employee resource;

	private List<Employee> resources;

	private List<Task> tasks;

	@JsonProperty(value = "notes")
	private String notes;

	@JsonProperty(value = "project")
	@JsonSerialize(using = ProjectInResourceAllocationSerializer.class)
	private Project project;

	public ResourceAllocation() {

	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	@JsonIgnore
	public List<Employee> getResources() {
		return resources;
	}

	@JsonProperty(value = "resources")
	public void setResources(List<Employee> resources) {
		this.resources = resources;
	}

	@JsonIgnore
	public List<Task> getTasks() {
		return tasks;
	}

	@JsonProperty(value = "tasks")
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
