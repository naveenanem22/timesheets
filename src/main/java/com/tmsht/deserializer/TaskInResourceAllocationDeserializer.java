package com.tmsht.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.tmsht.model.Task;

public class TaskInResourceAllocationDeserializer extends JsonDeserializer<Task> {

	@Override
	public Task deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		TreeNode treeNode = jp.getCodec().readTree(jp);
		TextNode id = (TextNode) treeNode.get("id");
		TextNode taskName = (TextNode) treeNode.get("taskName");
		Task task = new Task();
		task.setId(id.asInt());
		task.setName(taskName.asText());
		return task;
	}

}
