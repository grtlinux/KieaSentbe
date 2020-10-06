package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

@SuppressWarnings("unused")
public class JsonToStream {

	private LnsMstInfo lnsMstInfo;
	
	private JsonNode jsonDataNode;
	private JsonNode jsonHeadNode;
	private JsonNode jsonBodyNode;
	
	public JsonToStream(LnsMstInfo lnsMstInfo, String jsonData) throws Exception {
		this(lnsMstInfo, new ObjectMapper().readTree(jsonData));
	}
	
	public JsonToStream(LnsMstInfo lnsMstInfo, JsonNode jsonDataNode) {
		this.lnsMstInfo = lnsMstInfo;
		this.jsonDataNode = jsonDataNode;
		this.jsonHeadNode = this.jsonDataNode.at("/__head");
		this.jsonBodyNode = this.jsonDataNode.at("/__body");
		//System.out.println("KANG >>>>> " + this.jsonDataNode.at("/__head/length").asText());
		//System.out.println("KANG >>>>> " + this.jsonDataNode.at("/__head/length").textValue());
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getHeadStream());
		sb.append("\n");
		sb.append(this.getBodyStream());
		
		return sb.toString();
	}
	
	public String getHeadStream() {
		StringBuffer sb = new StringBuffer();
		
		JsonNode rootNode = this.lnsMstInfo.getHeadDataInfoNode();
		String prefix = "/__head";
		
		traverse(sb, rootNode, prefix);
		
		return sb.toString();
	}
	
	public String getBodyStream() {
		StringBuffer sb = new StringBuffer();
		
		JsonNode rootNode = this.lnsMstInfo.getBodyDataInfoNode();
		String prefix = "/__body";
		
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
				throw new RuntimeException("Not yet implements...");
			}
		}
	}
	
	private void traverseObject(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			node.fieldNames().forEachRemaining((String fieldName) -> {
				String subPrefix = LnsNodeTools.getPrefix(prefix, fieldName, "/");
				
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
				
				String subPrefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/");
				
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
				//throw new RuntimeException("");
				return;
			}
			
			LnsElementInfo info = new LnsElementInfo(String.valueOf(value));
			if (info.isUsable()) {
				JsonNode dataNode = this.jsonDataNode.at(prefix);
				
				Object data = null;
				if (dataNode.isTextual()) {
					data = dataNode.textValue();
				} else if (dataNode.isNumber()) {
					data = dataNode.numberValue();
				} else if (dataNode.isBoolean()) {
					data = dataNode.booleanValue();
				}
				
				if (data == null) {
					switch (info.getType()) {
					case "STRING":
					case "INT":
					case "LONG":
					case "DOUBLE":
					case "FLOAT":
					case "BOOLEAN":
						data = "";
						break;
					}
					info.setFormat("%" + info.getLength() + "s");
				}
				
				if (Flag.flag) System.out.printf(">>>>> %s [%s] %s%n", prefix, data, info.getFormat());
			}
		}
	}
}
