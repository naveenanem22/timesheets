package com.tmsht.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "taskName")
	private String name;

	@JsonProperty(value = "desc")
	private String description;

	@JsonProperty(value = "project")
	private Project project;
	@JsonProperty(value = "startDate")
	private LocalDate startDate;

	@JsonProperty(value = "endDate")
	private LocalDate endDate;

	public Task() {

	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", project=" + project
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
