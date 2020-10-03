package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json02Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String mappingJson = "{\n" + 
				"  \"_arraySize\" : {\n" + 
				"    \"phones\" : 5,\n" + 
				"    \"taskIds\" : 5\n" + 
				"  },\n" + 
				"\n" +
				"  \"name\" : \"L:20,T:string\",\n" + 
				"  \"salary\" : \"L:20,T:long\",\n" + 
				"  \"phones\" : [ {\n" + 
				"      \"phoneType\" : \"L:5,T:string\",\n" + 
				"      \"phoneNumber\" : \"L:15,T:string\"\n" + 
				"    } "  +
				"  ],\n" + 
				"  \"taskIds\" : [ \"L:3,T:int\" ],\n" + 
				"  \"address\" : {\n" + 
				"    \"street\" : \"L:50,T:string\",\n" + 
				"    \"city\" : \"L:50,T:string\"\n" + 
				"  }\n" + 
				"}";
		JsonNode mappingNode = new ObjectMapper().readTree(mappingJson);
		System.out.println(">>>>> " + mappingNode.toPrettyString());
		
		////////////////////////////////////////////////////////////////////
		
		String inputJson = "{\"name\":\"Jake\",\"salary\":3000,\"phones\":"
				+ "[{\"phoneType\":\"cell\",\"phoneNumber\":\"111-111-111\"},"
				+ "{\"phoneType\":\"work\",\"phoneNumber\":\"222-222-222\"}],"
				+"\"taskIds\":[11,22,33],"
				+ "\"address\":{\"street\":\"101 Blue Dr\",\"city\":\"White Smoke\"}}";
		inputJson = mappingJson;
		
		JsonNode rootNode = new ObjectMapper().readTree(inputJson);
		System.out.println("rootNode.getNodeType(): " + rootNode.getNodeType());
		System.out.println("rootNode.toPrettyJson() : " + rootNode.toPrettyString());
		
		System.out.println("rootNode: " + rootNode);
		traverse(rootNode, 1);
		
		////////////////////////////////////////////////////////////////////
		
		System.out.println("----------------------------------");
		System.out.println(">>>>> " + rootNode.at("/address/city").asText());
		System.out.println(">>>>> " + rootNode.at("/phones/0/phoneNumber").asText());
		System.out.println(">>>>> " + rootNode.at("/phones/1/phoneNumber").asText());
		System.out.println(">>>>> " + rootNode.at("/phones/5/phoneNumber").asText());
		System.out.println(">>>>> " + rootNode.at("/phones/1").isEmpty());
		System.out.println(">>>>> " + rootNode.at("/phones/5").isEmpty());
		((ArrayNode)rootNode.at("/taskIds")).add(88);
		System.out.println(">>>>> " + rootNode.at("/taskIds/0").numberValue());
		System.out.println(">>>>> " + rootNode.at("/taskIds/1").asInt());
		System.out.println(">>>>> " + rootNode.at("/taskIds/2").asText());
		System.out.println(">>>>> " + rootNode.at("/taskIds/2").getNodeType());
		System.out.println(">>>>> " + rootNode.at("/taskIds/3").asText());
		System.out.println(">>>>> " + rootNode.at("/taskIds/4").asText());
	}
	
	private void traverse(JsonNode node, int level) {
		if (node.getNodeType() == JsonNodeType.OBJECT) {
			traverseObject(node, level);
		} else if (node.getNodeType() == JsonNodeType.ARRAY) {
			traverseArray(node, level);
		} else {
			throw new RuntimeException("Not yet implemented...");
		}
	}
	
	private void traverseObject(JsonNode node, int level) {
		node.fieldNames().forEachRemaining((String fieldName) -> {
			JsonNode childNode = node.get(fieldName);
			printNode(childNode, fieldName, level);
			if (traversable(childNode)) {
				traverse(childNode, level + 1);
			}
		});
	}
	
	private void traverseArray(JsonNode node, int level) {
		for (JsonNode jsonNode : node) {
			printNode(jsonNode, "arrayElement", level);
			if (traversable(jsonNode)) {
				traverse(jsonNode, level + 1);
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		boolean isObject = node.getNodeType() == JsonNodeType.OBJECT;
		boolean isArray = node.getNodeType() == JsonNodeType.ARRAY;
		return isObject || isArray;
	}
	
	private void printNode(JsonNode node, String keyName, int level) {
		int sizIndent = level * 4 - 3;
		if (traversable(node)) {
			System.out.printf("%" + sizIndent + "s|-- %s(%s)%n"
					, "", keyName, node.getNodeType());
		} else {
			Object value = null;
			if (node.isTextual()) {
				value = node.textValue();
			} else if (node.isNumber()) {
				value = node.numberValue();
			} else if (node.isBoolean()) {
				value = node.booleanValue();
			}
			System.out.printf("%" + sizIndent + "s|-- %s(%s) = %s%n"
					, "", keyName, node.getNodeType(), value);
		}
	}
}
