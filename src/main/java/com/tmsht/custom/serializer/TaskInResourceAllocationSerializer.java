package com.tmsht.custom.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tmsht.model.Task;

public class TaskInResourceAllocationSerializer extends JsonSerializer<Task> {

	@Override
	public void serialize(Task task, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", task.getId());
		gen.writeStringField("taskName", task.getName());
		gen.writeEndObject();

	}

}
