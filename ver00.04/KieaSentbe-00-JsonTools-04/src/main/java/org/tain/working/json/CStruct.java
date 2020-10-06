package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class CStruct {

	private LnsMstInfo lnsMstInfo;
	
	public CStruct(LnsMstInfo lnsMstInfo) {
		this.lnsMstInfo = lnsMstInfo;
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getHeadCStruct());
		sb.append(this.getBodyCStruct());
		
		return sb.toString();
	}
	
	public String getHeadCStruct() {
		StringBuffer sb = new StringBuffer();
		
		JsonNode rootNode = this.lnsMstInfo.getHeadDataInfoNode();
		String prefix = "";
		
		traverse(sb, rootNode, prefix);
		
		return sb.toString();
	}
	
	public String getBodyCStruct() {
		StringBuffer sb = new StringBuffer();
		
		JsonNode rootNode = this.lnsMstInfo.getBodyDataInfoNode();
		String prefix = "";
		
		traverse(sb, rootNode, prefix);
		
		return sb.toString();
	}
	
	private void traverse(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			if (node.getNodeType() == JsonNodeType.OBJECT) {
				traverseObject(sb, node, prefix);
			} else if (node.getNodeType() == JsonNodeType.ARRAY) {
				traverseArray(sb, node, prefix);
			} else {
				throw new RuntimeException("Not yet implemented... [by Kiea Seok Kang]");
			}
		}
	}
	
	private void traverseObject(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			node.fieldNames().forEachRemaining((String fieldName) -> {
				String subPrefix = LnsNodeTools.getPrefix(prefix, fieldName);
				
				JsonNode childNode = node.get(fieldName);
				processNode(sb, childNode, fieldName, subPrefix);
				if (traversable(childNode)) {
					traverse(sb, childNode, subPrefix);
				}
			});
		}
	}
	
	private void traverseArray(StringBuffer sb, JsonNode node, String prefix) {
		if (!Flag.flag) {
			/*
			int index = -1;
			for (JsonNode itemNode : node) {
				String subPrefix = LnsNodeTools.getPrefix(prefix, String.valueOf(++index));
				
				processNode(sb, itemNode, "arrayElements", subPrefix);
				if (traversable(itemNode)) {
					traverse(sb, itemNode, subPrefix);
				}
			}
			*/
		}
		if (Flag.flag) {
			for (int index=0; index < 5; index++) {
				JsonNode itemNode = node.at("/" + index);
				if (itemNode.isEmpty()) {
					itemNode = node.at("/0");
				}
				
				String subPrefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index));
				
				processNode(sb, itemNode, "arrayElements", subPrefix);
				if (traversable(itemNode)) {
					traverse(sb, itemNode, subPrefix);
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
		String line = null;
		if (traversable(node)) {
			line = String.format("%-30s   %s(%s)%n", prefix, keyName, node.getNodeType());
			//if (Flag.flag) System.out.print(">>>>> " + line);
			//sb.append(line);
		} else {
			Object value = null;
			if (node.isTextual()) {
				value = node.textValue();
			} else if (node.isNumber()) {
				value = node.numberValue();
			} else if (node.isBoolean()) {
				value = node.booleanValue();
			} else {
				throw new RuntimeException("");
			}
			//line = String.format("%-30s   %s(%s) = %s%n", prefix, keyName, node.getNodeType(), value);
			LnsElementInfo info = new LnsElementInfo(String.valueOf(value));
			line = String.format("char %-30s   [%3d]; /* %s */%n", prefix, info.getLength(), "comment");
			//if (Flag.flag) System.out.print(">>>>> " + line);
			if (info.isUsable()) sb.append(line);
		}
	}
}
