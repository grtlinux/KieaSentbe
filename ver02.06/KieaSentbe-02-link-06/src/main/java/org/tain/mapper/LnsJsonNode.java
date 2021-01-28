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
		this("{}");  // JsonNode
	}
	
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
	
	public String toString() {
		return this.jsonNode.toString();
	}
	
	public String toPrettyString() {
		return this.jsonNode.toPrettyString();
	}
	
	///////////////////////////////////////////////////////////////
	
	public List<String> fieldNames() {
		List<String> lstNames = new ArrayList<>();
		this.jsonNode.fieldNames().forEachRemaining((String fieldName) -> {
			lstNames.add(fieldName);
		});
		return lstNames;
	}
	
	///////////////////////////////////////////////////////////////
	
	// ObjectNode
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
		} else if (obj instanceof ObjectNode) {
			this.objectNode.set(fieldName, (ObjectNode) obj);
		} else if (obj instanceof ArrayNode) {
			this.objectNode.set(fieldName, (ArrayNode) obj);
		}
	}
	
	public void put(String branch, String fieldName, Object obj) {
		ObjectNode objectNode = (ObjectNode) this.jsonNode.at(branch);
		if (obj instanceof String) {
			objectNode.put(fieldName, (String)obj);
		} else if (obj instanceof Long) {
			objectNode.put(fieldName, (Long) obj);
		} else if (obj instanceof Integer) {
			objectNode.put(fieldName, (Integer) obj);
		} else if (obj instanceof Double) {
			objectNode.put(fieldName, (Double) obj);
		} else if (obj instanceof Float) {
			objectNode.put(fieldName, (Float) obj);
		} else if (obj instanceof Boolean) {
			objectNode.put(fieldName, (Boolean) obj);
		} else if (obj instanceof JsonNode) {
			objectNode.set(fieldName, (JsonNode) obj);
		} else if (obj instanceof ObjectNode) {
			objectNode.set(fieldName, (ObjectNode) obj);
		} else if (obj instanceof ArrayNode) {
			objectNode.set(fieldName, (ArrayNode) obj);
		}
	}
	
	// ArrayNode
	public void add(Object obj) {
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
		} else if (obj instanceof ObjectNode) {
			this.arrayNode.add((ObjectNode) obj);
		} else if (obj instanceof ArrayNode) {
			this.arrayNode.add((ArrayNode) obj);
		}
	}
	
	public void add(String branch, Object obj) {
		ArrayNode arrayNode = (ArrayNode) this.jsonNode.at(branch);
		if (obj instanceof String) {
			arrayNode.add((String)obj);
		} else if (obj instanceof Long) {
			arrayNode.add((Long) obj);
		} else if (obj instanceof Integer) {
			arrayNode.add((Integer) obj);
		} else if (obj instanceof Double) {
			arrayNode.add((Double) obj);
		} else if (obj instanceof Float) {
			arrayNode.add((Float) obj);
		} else if (obj instanceof Boolean) {
			arrayNode.add((Boolean) obj);
		} else if (obj instanceof JsonNode) {
			arrayNode.add((JsonNode) obj);
		} else if (obj instanceof ObjectNode) {
			arrayNode.add((ObjectNode) obj);
		} else if (obj instanceof ArrayNode) {
			arrayNode.add((ArrayNode) obj);
		}
	}
	
	///////////////////////////////////////////////////////////////
	
	public JsonNode get() {
		return getJsonNode();
	}
	
	public JsonNode getJsonNode() {
		return this.jsonNode;
	}
	
	///////////////////////////////////////////////////////////////
	
	@Deprecated
	@SuppressWarnings("unused")
	public String getValue(String fieldPath) {
		LnsSpliter spliter = new LnsSpliter(fieldPath);
		return this.jsonNode.get(fieldPath).textValue();
	}
	
	///////////////////////////////////////////////////////////////
	
	public String getText(String branch, String fieldName) {
		return this.jsonNode.at(branch).get(fieldName).textValue();
	}
	
	public Number getNumber(String branch, String fieldName) {
		return this.jsonNode.at(branch).get(fieldName).numberValue();
	}
	
	public Boolean getBoolean(String branch, String fieldName) {
		return this.jsonNode.at(branch).get(fieldName).booleanValue();
	}
	
	public JsonNode getJsonNode(String branch, String fieldName) {
		return (JsonNode) this.jsonNode.at(branch).get(fieldName);
	}
	
	public ObjectNode getObjectNode(String branch, String fieldName) {
		return (ObjectNode) this.jsonNode.at(branch).get(fieldName);
	}
	
	public ArrayNode getArrayNode(String branch, String fieldName) {
		return (ArrayNode) this.jsonNode.at(branch).get(fieldName);
	}
	
	///////////////////////////////////////////////////////////////
	
	public String getText(String fieldName) {
		return this.jsonNode.get(fieldName).textValue();
	}
	
	public Number getNumber(String fieldName) {
		return this.jsonNode.get(fieldName).numberValue();
	}
	
	public Boolean getBoolean(String fieldName) {
		return this.jsonNode.get(fieldName).booleanValue();
	}
	
	public JsonNode getJsonNode(String fieldName) {
		return (JsonNode) this.jsonNode.get(fieldName);
	}
	
	public ObjectNode getObjectNode(String fieldName) {
		return (ObjectNode) this.jsonNode.get(fieldName);
	}
	
	public ArrayNode getArrayNode(String fieldName) {
		return (ArrayNode) this.jsonNode.get(fieldName);
	}
	
	///////////////////////////////////////////////////////////////
}
