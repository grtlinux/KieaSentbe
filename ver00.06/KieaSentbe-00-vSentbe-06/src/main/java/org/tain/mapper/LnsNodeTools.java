package org.tain.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		return prefix + split + fieldName;
	}
	
	public static String getPrefix(String prefix, String fieldName) {
		return getPrefix(prefix, fieldName, "_");
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static LnsSpliter split(String atPath) {
		int index = atPath.lastIndexOf('/');
		String pathName = atPath.substring(0, index);
		String fieldName = atPath.substring(index + 1);
		LnsSpliter lnsSpliter = new LnsSpliter(pathName, fieldName);
		return lnsSpliter;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static JsonNode getJsonNode(String strJson) throws Exception {
		return new ObjectMapper().readTree(strJson);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static String getDate() {
		return new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
	}
	
	public static String getTime() {
		return new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
}
