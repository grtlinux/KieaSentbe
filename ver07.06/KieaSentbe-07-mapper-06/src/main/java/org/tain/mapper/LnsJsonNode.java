package org.tain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LnsJsonNode {

	private JsonNode jsonNode;  // ObjectNode or ArrayNode
	private ObjectNode objectNode;
	private ArrayNode arrayNode;
	
	///////////////////////////////////////////////////////////////
	
	public LnsJsonNode(JsonNode jsonNode) {
		this.jsonNode = jsonNode;
		if (jsonNode.isObject())
			this.objectNode = (ObjectNode) jsonNode;
		if (jsonNode.isArray())
			this.arrayNode = (ArrayNode) jsonNode;
	}
	
	public LnsJsonNode(String strJson) throws Exception {
		this(new ObjectMapper().readTree(strJson));
	}
	
	public LnsJsonNode() throws Exception {
		this("{}");
	}
	
	///////////////////////////////////////////////////////////////
	
	public JsonNode get() {
		return this.jsonNode;
	}
	
	public String toString() {
		return this.jsonNode.toString();
	}
	
	public String toPrettyString() {
		return this.jsonNode.toPrettyString();
	}
	
	public List<String> fieldNames() {
		List<String> lstNames = new ArrayList<>();
		this.jsonNode.fieldNames().forEachRemaining((String fieldName) -> {
			lstNames.add(fieldName);
		});
		return lstNames;
	}
	
	public String value(String fieldName) {
		return this.jsonNode.get(fieldName).textValue();
	}
	
	///////////////////////////////////////////////////////////////
	
	public void put(String fieldName, Object obj) {
		if (obj instanceof String) {
			this.objectNode.put(fieldName, (String)obj);
		} else if (obj instanceof Long) {
			this.objectNode.put(fieldName, (Long) obj);
		} else if (obj instanceof Integer) {
			this.objectNode.put(fieldName, (Integer) obj);
		} else if (obj instanceof Double) {
			this.objectNode.put(fieldName, (Double) obj);
		} else if (obj instanceof Float) {
			this.objectNode.put(fieldName, (Float) obj);
		} else if (obj instanceof Boolean) {
			this.objectNode.put(fieldName, (Boolean) obj);
		} else if (obj instanceof JsonNode) {
			this.objectNode.set(fieldName, (JsonNode) obj);
		} else if (obj instanceof ArrayNode) {
			this.objectNode.set(fieldName, (ArrayNode) obj);
		}
	}
	
	public void put(Object obj) {
		if (obj instanceof String) {
			this.arrayNode.add((String)obj);
		} else if (obj instanceof Long) {
			this.arrayNode.add((Long) obj);
		} else if (obj instanceof Integer) {
			this.arrayNode.add((Integer) obj);
		} else if (obj instanceof Double) {
			this.arrayNode.add((Double) obj);
		} else if (obj instanceof Float) {
			this.arrayNode.add((Float) obj);
		} else if (obj instanceof Boolean) {
			this.arrayNode.add((Boolean) obj);
		} else if (obj instanceof JsonNode) {
			this.arrayNode.add((JsonNode) obj);
		} else if (obj instanceof ArrayNode) {
			this.arrayNode.add((ArrayNode) obj);
		}
	}
}
