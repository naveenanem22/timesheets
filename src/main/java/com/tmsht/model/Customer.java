package com.tmsht.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

	@JsonProperty(value = "id")
	private int id;

	public Customer() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + "]";
	}

}
