package com.tmsht.custom.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tmsht.model.Project;

public class ProjectInResourceAllocationSerializer extends JsonSerializer<Project> {

	@Override
	public void serialize(Project project, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", project.getId());
		gen.writeStringField("projectId", project.getProjectId());
		gen.writeEndObject();

	}

}
