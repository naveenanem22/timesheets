package com.tmsht.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pc.deserializers.JsonDateDeserializer;
import com.pc.serializers.JsonDateSerializer;
import com.tmsht.custom.serializer.CustomerInProjectSerializer;

public class Project {

	@JsonProperty(value = "id")
	private int id;

	@JsonProperty(value = "tasks")
	private List<Task> tasks;

	@JsonProperty(value = "title")
	private String name;

	@JsonProperty(value = "projectId")
	private String projectId;

	@JsonProperty(value = "serviceType")
	private String serviceType;

	@JsonProperty(value = "description")
	private String description;

	@JsonProperty(value = "plannedStartDate")
	private LocalDate plannedStartDate;

	@JsonProperty(value = "plannedEndDate")
	private LocalDate plannedEndDate;

	@JsonProperty(value = "actualStartDate")
	private LocalDate actualStartDate;

	@JsonProperty(value = "createdDate")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime createdDate;

	@JsonProperty(value = "updatedDate")
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime updatedDate;

	@JsonProperty(value = "actualEndDate")
	private LocalDate actualEndDate;

	@JsonProperty(value = "status")
	private String status;

	@JsonProperty(value = "notes")
	private String notes;

	@JsonProperty(value = "customer")
	@JsonSerialize(using = CustomerInProjectSerializer.class)
	private Customer customer;

	public Project() {

	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(LocalDate plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public LocalDate getPlannedEndDate() {
		return plannedEndDate;
	}

	public void setPlannedEndDate(LocalDate plannedEndDate) {
		this.plannedEndDate = plannedEndDate;
	}

	public LocalDate getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(LocalDate actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public LocalDate getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(LocalDate actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", tasks=" + tasks + ", name=" + name + ", projectId=" + projectId
				+ ", serviceType=" + serviceType + ", description=" + description + ", plannedStartDate="
				+ plannedStartDate + ", plannedEndDate=" + plannedEndDate + ", actualStartDate=" + actualStartDate
				+ ", actualEndDate=" + actualEndDate + ", status=" + status + ", notes=" + notes + ", customer="
				+ customer + "]";
	}

}
