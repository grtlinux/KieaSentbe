package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class _Base {

	private _LnsJsonMstInfo jsonMstInfo;
	
	public _Base(_LnsJsonMstInfo jsonMstInfo) {
		this.jsonMstInfo = jsonMstInfo;
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		JsonNode rootNode = this.jsonMstInfo.getJsonInfoNode();
		String prefix = "";
		
		traverse(sb, rootNode, prefix);
		
		return sb.toString();
	}
	
	private void traverse(StringBuffer sb, JsonNode node, String prefix) {
		if (node.getNodeType() == JsonNodeType.OBJECT) {
			traverseObject(sb, node, prefix);
		} else if (node.getNodeType() == JsonNodeType.ARRAY) {
			traverseArray(sb, node, prefix);
		} else {
			throw new RuntimeException("Not yet implemented...");
		}
	}
	
	private void traverseObject(StringBuffer sb, JsonNode node, String prefix) {
		node.fieldNames().forEachRemaining((String fieldName) -> {
			JsonNode childNode = node.get(fieldName);
			processNode(sb, childNode, fieldName, prefix + "/" + fieldName);
			if (traversable(childNode)) {
				traverse(sb, childNode, prefix + "/" + fieldName);
			}
		});
	}
	
	private void traverseArray(StringBuffer sb, JsonNode node, String prefix) {
		if (!Flag.flag) {
			/*
			int index = 0;
			for (JsonNode itemNode : node) {
				processNode(sb, itemNode, "arrayElements", prefix + "_" + index);
				if (traversable(itemNode)) {
					traverse(sb, itemNode, prefix + "_" + index);
				}
				index ++;
			}
			*/
		}
		
		int arrSize = -1;
		if (Flag.flag) {
			String arrName = prefix + "__arrSize";
			JsonNode mstInfoNode = this.jsonMstInfo.getMstInfoNode();
			arrSize = mstInfoNode.get(arrName).asInt();
			
			if (Flag.flag) System.out.println(">>>>> " + prefix + "__arrSize = " + arrSize);
		}
		
		if (Flag.flag) {
			for (int index=0; index < arrSize; index++) {
				JsonNode itemNode = node.at("/" + index);
				//if (itemNode.isNull()) System.out.println("NULL");
				if (itemNode.isEmpty()) {
					itemNode = node.at("/0");
				}
				processNode(sb, itemNode, "arrayElements", prefix + "/" + index);
				if (traversable(itemNode)) {
					traverse(sb, itemNode, prefix + "/" + index);
				}
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		boolean isObject = node.getNodeType() == JsonNodeType.OBJECT;
		boolean isArray = node.getNodeType() == JsonNodeType.ARRAY;
		return isObject || isArray;
	}
	
	private void processNode(StringBuffer sb, JsonNode node, String keyName, String prefix) {
		if (traversable(node)) {
			String line = String.format(".%s..%s(%s)%n", prefix, keyName, node.getNodeType());
			if (!Flag.flag) System.out.print(line);
			if (!Flag.flag) sb.append(line);
		} else {
			Object value = null;
			if (node.isTextual()) {
				value = node.textValue();
			} else if (node.isNumber()) {
				value = node.numberValue();
			} else if (node.isBoolean()) {
				value = node.booleanValue();
			}
			String line = String.format(".%s..%s(%s) = %s%n", prefix, keyName, node.getNodeType(), value);
			if (!Flag.flag) System.out.print(line);
			sb.append(line);
		}
	}
}
