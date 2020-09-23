package org.tain.object.lns;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class LnsMap implements Cloneable {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	/*
	 * Map <-> JsonNode <-> JsonString
	 */
	private Map<String, String> map;
	private JsonNode jsonNode;
	private String jsonString;
	
	////////////////////////////////////////////////////////////////////////

	public LnsMap() throws Exception {
		this.map = new HashMap<>();
		this.jsonString = "{}";
		this.jsonNode = this.objectMapper.readTree(this.jsonString);
	}
	
	public LnsMap(String jsonString) throws Exception {
		this.jsonString = jsonString;
		this.map = this.objectMapper.readValue(this.jsonString, new TypeReference<Map<String,String>>(){});
		this.jsonNode = this.objectMapper.readTree(this.jsonString);
	}
	
	public LnsMap(Map<String,String> map) throws Exception {
		this.map = map;
		this.jsonString = this.objectMapper.writeValueAsString(this.map);
		this.jsonNode = this.objectMapper.readTree(this.jsonString);
	}
	
	public LnsMap(JsonNode jsonNode) throws Exception {
		this.jsonNode = jsonNode;
		this.jsonString = jsonNode.toString();
		this.map = this.objectMapper.readValue(this.jsonString, new TypeReference<Map<String,String>>(){});
	}
	
	////////////////////////////////////////////////////////////////////////

	public void put(String key, String value) {
		this.map.put(key, value);
	}
	
	public String get(String key) {
		return this.map.get(key);
	}
	
	public void reset() throws Exception {
		this.jsonString = this.objectMapper.writeValueAsString(this.map);
		this.jsonNode = this.objectMapper.readTree(this.jsonString);
	}
	
	////////////////////////////////////////////////////////////////////////

	public String toString() {
		return this.jsonNode.toString();
	}
	
	public String toJson() {
		return this.jsonNode.toString();
	}
	
	public String toPrettyJson() {
		return this.jsonNode.toPrettyString();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	@Override
	public LnsMap clone() throws CloneNotSupportedException {
		return (LnsMap) super.clone();
	}
	
	///////////////////////////////////////////////////////////////////////
	
	public void printInfo() {
		System.out.println("======================= LnsMap Infor =====================");
		System.out.println("LnsMap >>>>> jsonString = " + jsonString);
		System.out.println("LnsMap >>>>> jsonNode = " + jsonNode);
		System.out.println("LnsMap >>>>> map = " + map);
		System.out.println("=========================================================");
	}
}
