package org.tain.working.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LnsNodeTools {

	public static JsonNode put(JsonNode node, String path, String value) {
		int idx = path.lastIndexOf('/');
		String pathAt = path.substring(0, idx);
		String pathName = path.substring(idx+1);
		((ObjectNode) node.at(pathAt)).put(pathName, value);
		return node;
	}
	
	public static JsonNode put(JsonNode node, String path, Integer value) {
		int idx = path.lastIndexOf('/');
		String pathAt = path.substring(0, idx);
		String pathName = path.substring(idx+1);
		((ObjectNode) node.at(pathAt)).put(pathName, value);
		return node;
	}
	
	public static JsonNode put(JsonNode node, String path, Long value) {
		int idx = path.lastIndexOf('/');
		String pathAt = path.substring(0, idx);
		String pathName = path.substring(idx+1);
		((ObjectNode) node.at(pathAt)).put(pathName, value);
		return node;
	}
	
	public static JsonNode put(JsonNode node, String path, Double value) {
		int idx = path.lastIndexOf('/');
		String pathAt = path.substring(0, idx);
		String pathName = path.substring(idx+1);
		((ObjectNode) node.at(pathAt)).put(pathName, value);
		return node;
	}
	
	public static JsonNode put(JsonNode node, String path, Boolean value) {
		int idx = path.lastIndexOf('/');
		String pathAt = path.substring(0, idx);
		String pathName = path.substring(idx+1);
		((ObjectNode) node.at(pathAt)).put(pathName, value);
		return node;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static String getText(JsonNode node, String path) {
		return node.at(path).asText();
	}
	
	public static Integer getInteger(JsonNode node, String path) {
		return node.at(path).asInt();
	}
	
	public static Long getLong(JsonNode node, String path) {
		return node.at(path).asLong();
	}
	
	public static Double getDouble(JsonNode node, String path) {
		return node.at(path).asDouble();
	}
	
	public static Boolean getBoolean(JsonNode node, String path) {
		return node.at(path).asBoolean();
	}
	
	public static Number getNumber(JsonNode node, String path) {
		return node.at(path).numberValue();
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static String getPrefix(String prefix, String fieldName, String split) {
		if (!"".equals(prefix)) {
			return prefix + split + fieldName;
		}
		return fieldName;
	}
	
	public static String getPrefix(String prefix, String fieldName) {
		return getPrefix(prefix, fieldName, "_");
	}
}
