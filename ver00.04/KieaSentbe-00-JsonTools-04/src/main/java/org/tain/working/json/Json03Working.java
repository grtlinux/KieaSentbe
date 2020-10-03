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
		
		String mappingJson = "{\n" + 
				"  \"name\" : \"Jake\",\n" + 
				"  \"salary\" : 3000,\n" + 
				"  \"phones\" : [ {\n" + 
				"      \"phoneType\" : \"cell\",\n" + 
				"      \"phoneNumber\" : \"111-111-111\"\n" + 
				"    }, {\n" + 
				"      \"phoneType\" : \"work\",\n" + 
				"      \"phoneNumber\" : \"222-222-222\"\n" + 
				"    } "  +
				"  ],\n" + 
				"  \"taskIds\" : [ 11, 22, 33 ],\n" + 
				"  \"address\" : {\n" + 
				"    \"street\" : \"101 Blue Dr\",\n" + 
				"    \"city\" : \"White Smoke\"\n" + 
				"  }\n" + 
				"}";
		
		JsonNode rootNode = new ObjectMapper().readTree(mappingJson);
		System.out.println(">>>>> " + rootNode.toPrettyString());
		
		traverse(rootNode, 1);
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
			}
			System.out.printf("%" + sizIndent + "s|-- %s(%s) = %s%n"
					, "", keyName, node.getNodeType(), value);
		}
	}
}
