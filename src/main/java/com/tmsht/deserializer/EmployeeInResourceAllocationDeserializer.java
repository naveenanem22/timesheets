package com.tmsht.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.pmt.model.Employee;

public class EmployeeInResourceAllocationDeserializer extends JsonDeserializer<Employee> {

	@Override
	public Employee deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		TreeNode treeNode = jp.getCodec().readTree(jp);
		TextNode firstName = (TextNode) treeNode.get("firstName");
		TextNode lastName = (TextNode) treeNode.get("lastName");
		Employee resource = new Employee();
		resource.setFirstName(firstName.asText());
		resource.setLastName(lastName.asText());
		return resource;
	}

}
