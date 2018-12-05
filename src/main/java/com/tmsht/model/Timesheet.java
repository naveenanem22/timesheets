package com.tmsht.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pc.deserializers.JsonDateDeserializer;
import com.pc.serializers.JsonDateSerializer;

public class Timesheet {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "task")
	private Task task;

	@JsonProperty(value = "project")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private Project project;

	@JsonProperty(value = "date")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDate date;

	@JsonProperty(value = "startTime")
	private LocalTime startTime;

	@JsonProperty(value = "endTime")
	private LocalTime endTime;

	@JsonProperty(value = "status")
	private String status;

	public Timesheet() {

	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Timesheet [id=" + id + ", task=" + task + ", project=" + project + ", date=" + date + ", startTime="
				+ startTime + ", endTime=" + endTime + ", status=" + status + "]";
	}

}
