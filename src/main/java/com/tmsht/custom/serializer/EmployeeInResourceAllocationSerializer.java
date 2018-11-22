package com.tmsht.custom.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pmt.model.Employee;

public class EmployeeInResourceAllocationSerializer extends JsonSerializer<Employee> {

	@Override
	public void serialize(Employee employee, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("firstName", employee.getFirstName());
		gen.writeStringField("lastName", employee.getLastName());		
		gen.writeEndObject();

	}

}
