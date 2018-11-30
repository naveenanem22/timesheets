package com.tmsht.util.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pmt.common.JsonDateSerializer;


public class ApiError {
	@JsonProperty(value = "timestamp")
	@JsonSerialize(using = JsonDateSerializer.class)
	private LocalDateTime timestamp;
	
	@JsonProperty(value = "erroMessage")
	private String message;
	
	@JsonProperty(value = "errorDetails")
	private List<String> details;

	public ApiError(LocalDateTime timestamp, String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

}
