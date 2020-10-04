package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json03Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String jsonInfo = "{\n" + 
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
				"    \"city\" : \"L:50,T:string\",\n" + 
				"    \"usable\" : \"L:5,T:boolean\"\n" + 
				"  }\n" + 
				"}";
		JsonNode jsonNodeInfo = new ObjectMapper().readTree(jsonInfo);
		System.out.println(">>>>> " + jsonNodeInfo.toPrettyString());
		
		String jsonInput = "{\n" + 
				"  \"name\" : \"Jake\",\n" + 
				"  \"salary\" : 3000.5,\n" + 
				"  \"phones\" : [ {\n" + 
				"      \"phoneType\" : \"cell\",\n" + 
				"      \"phoneNumber\" : \"111-111-111\"\n" + 
				"    }, {\n" + 
				"      \"phoneType\" : \"work\",\n" + 
				"      \"phoneNumber\" : \"222-222-222\"\n" + 
				"    } "  +
				"  ],\n" + 
				"  \"taskIds\" : [ 11, 22.2, 33000 ],\n" + 
				"  \"address\" : {\n" + 
				"    \"street\" : \"101 Blue Dr\",\n" + 
				"    \"city\" : \"White Smoke\",\n" + 
				"    \"usable\" : true\n" + 
				"  }\n" + 
				"}";
		
		JsonNode rootNode = new ObjectMapper().readTree(jsonInput);
		System.out.println(">>>>> " + rootNode.toPrettyString());
		
		traverse(rootNode, 1);
	}
	
	private void traverse(JsonNode node, int level) {
		if (node.getNodeType() == JsonNodeType.OBJECT) {
			traverseObject(node, level);
		} else if (node.getNodeType() == JsonNodeType.ARRAY) {
			traverseArray(node, level);
		} else {
			throw new RuntimeException("not yet implemented...");
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
		for (JsonNode itemNode : node) {
			printNode(itemNode, "arrayElement", level);
			if (traversable(itemNode)) {
				traverse(itemNode, level + 1);
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		boolean isObject = node.getNodeType() == JsonNodeType.OBJECT;
		boolean isArray = node.getNodeType() == JsonNodeType.ARRAY;
		return isObject || isArray;
	}
	
	private void printNode(JsonNode node, String keyName, int level) {
		int indent = level * 4 - 3;
		if (traversable(node)) {
			System.out.printf("%" + indent + "s|-- %s(%s)%n"
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
			System.out.printf("%" + indent + "s|-- %s(%s) = %s%n"
					, "", keyName, node.getNodeType(), value);
		}
	}
}
