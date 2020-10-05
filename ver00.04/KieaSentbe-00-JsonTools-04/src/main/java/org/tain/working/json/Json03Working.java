package org.tain.working.json;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json03Working {

	/*
	 * test01()
	 */
	@SuppressWarnings("unused")
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String jsonInfo = "{\n" + 
				"  \"__masterInfo\" : {\n" + 
				"    \"name\" : \"helloMstInfo\",\n" + 
				"    \"phones__arrSize\" : 5,\n" + 
				"    \"taskIds__arrSize\" : 5\n" + 
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
		
		////////////////////////////////////////////////////////////////////
		
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
		
		traverse(rootNode, 1, "root");
		
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
		JsonNode node = rootNode.at("/address");
		if (!Flag.flag && node instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) node;
			//objectNode.remove("usable");
			objectNode.removeAll();
		}
		System.out.println(">>>>> rootNode = " + rootNode.toPrettyString());
		if (Flag.flag && rootNode instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) rootNode;
			objectNode.remove("address");
			//objectNode.removeAll();
		}
		System.out.println(">>>>> rootNode = " + rootNode.toPrettyString());
		
		////////////////////////////////////////////////////////////////////
		
		if (Flag.flag) {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode objectNode = objectMapper.createObjectNode();
			ArrayNode arrayNode = objectMapper.createArrayNode();
		}
		if (Flag.flag) {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode parentNode = objectMapper.createObjectNode();
			JsonNode childNode = null;  //readJsonIntoJsonNode();
			parentNode.set("child1", childNode);
			parentNode.put("field1", "value1");
			parentNode.put("field2", 12345);
			parentNode.put("field3", 999.999);
			parentNode.put("field4", false);
			parentNode.remove("field3");
			System.out.println(">>>>> parentNode = " + parentNode.toPrettyString());
		}
		if (Flag.flag) {
			Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
			while (fields.hasNext()) {
				Map.Entry<String, JsonNode> field = fields.next();
				String fieldName = field.getKey();
				JsonNode fieldValue = field.getValue();
			}
		}
		if (Flag.flag) {
			Iterator<String> fieldNames = rootNode.fieldNames();
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				JsonNode fieldNode = rootNode.get(fieldName);
			}
		}
	}
	
	private void traverse(JsonNode node, int level, String prefix) {
		if (node.getNodeType() == JsonNodeType.OBJECT) {
			traverseObject(node, level, prefix);
		} else if (node.getNodeType() == JsonNodeType.ARRAY) {
			traverseArray(node, level, prefix);
		} else {
			throw new RuntimeException("Not yet implemented....");
		}
	}
	
	private void traverseObject(JsonNode node, int level, String prefix) {
		String prefix2 = prefix + "_";
		node.fieldNames().forEachRemaining((String fieldName) -> {
			JsonNode childNode = node.get(fieldName);
			printNode(childNode, fieldName, level, prefix2);
			if (traversable(childNode)) {
				traverse(childNode, level + 1, prefix2 + fieldName);
			}
		});
	}
	
	private void traverseArray(JsonNode node, int level, String prefix) {
		int index = 0;
		for (JsonNode itemNode : node) {
			String prefix2 = prefix + "_" + index + "_";
			printNode(itemNode, "arrayElement", level, prefix2);
			if (traversable(itemNode)) {
				traverse(itemNode, level + 1, prefix2);
			}
			index ++;
		}
	}
	
	private boolean traversable(JsonNode node) {
		boolean isObject = node.getNodeType() == JsonNodeType.OBJECT;
		boolean isArray = node.getNodeType() == JsonNodeType.ARRAY;
		return isObject || isArray;
	}
	
	private void printNode(JsonNode node, String keyName, int level, String prefix) {
		int indent = level * 4 - 3;
		if (traversable(node)) {
			System.out.printf("%" + indent + "s|-- %s(%s)%n"
					, "", prefix + keyName, node.getNodeType());
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
					, "", prefix + keyName, node.getNodeType(), value);
		}
	}
	
	/*
	 * test02()
	 */
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String jsonInfo = "{\n" + 
				"  \"__masterInfo\" : {\n" + 
				"    \"name\" : \"helloMstInfo\",\n" + 
				"    \"phones__arrSize\" : 5,\n" + 
				"    \"taskIds__arrSize\" : 5\n" + 
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
		System.out.println("1-1 >>>>> " + jsonNodeInfo.toPrettyString());
		
		((ObjectNode) jsonNodeInfo).remove("__masterInfo");
		System.out.println("1-2 >>>>> " + jsonNodeInfo.toPrettyString());
		
		////////////////////////////////////////////////////////////////////
		
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
		System.out.println("2-1 >>>>> " + rootNode.toPrettyString());
		
		traverse(rootNode, 1, "root");
	}
}
