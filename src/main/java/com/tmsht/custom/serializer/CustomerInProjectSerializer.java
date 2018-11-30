package com.tmsht.custom.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tmsht.model.Customer;

public class CustomerInProjectSerializer extends JsonSerializer<Customer> {

	@Override
	public void serialize(Customer customer, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("id", customer.getId());
		gen.writeEndObject();

	}

}
