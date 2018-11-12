package com.tmsht.model;

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

	public Task() {

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

}
