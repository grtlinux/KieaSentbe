package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LnsStreamToJson {

	private LnsMstInfo lnsMstInfo;
	
	private String streamData;
	private int offset = 0;
	
	private ObjectNode parentNode;
	//private JsonNode jsonParentNode;
	
	public LnsStreamToJson(LnsMstInfo lnsMstInfo, String streamData) {
		this.lnsMstInfo = lnsMstInfo;
		this.streamData = streamData;
		//this.jsonHeadNode = this.jsonDataNode.at("/__head");
		//this.jsonBodyNode = this.jsonDataNode.at("/__body");
		//System.out.println("KANG >>>>> " + this.jsonDataNode.at("/__head/length").asText());
		//System.out.println("KANG >>>>> " + this.jsonDataNode.at("/__head/length").textValue());
		
		this.parentNode = new ObjectMapper().createObjectNode();
		this.parentNode.set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		this.parentNode.set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		//this.jsonParentNode = (JsonNode) this.parentNode;
		//if (Flag.flag) System.out.println(">>>>> jsonParentNode = " + this.jsonParentNode.toPrettyString());
		
		jobForArray();
	}
	
	private void jobForArray() {
		JsonNode bodyBaseInfoNode = this.lnsMstInfo.getBodyBaseInfoNode();
		bodyBaseInfoNode.fieldNames().forEachRemaining((String fieldName) -> {
			if (fieldName.contains("__arrSize")) {
				String prefix = "/__body" + fieldName.replaceAll("__arrSize", "");
				System.out.printf(">>>>> jobForArray: %s %s%n", fieldName, prefix);
			}
		});
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getHeadStream());
		//sb.append("\n");
		sb.append(this.getBodyStream());
		
		if (Flag.flag) {
			System.out.println(">>>>> parentNode = " + this.parentNode.toPrettyString());
		}
		
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
				String subPrefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/");
				
				JsonNode itemNode = node.at("/" + index);
				if (itemNode.isEmpty()) {
					itemNode = node.at("/0");
				}
				
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
			if (!Flag.flag) System.out.print(">>>>> " + line);
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
			
			// element info
			LnsElementInfo info = new LnsElementInfo(String.valueOf(value));
			if (info.isUsable()) {
				String data = this.streamData.substring(offset, offset + info.getLength());
				offset += info.getLength();
				
				if (Flag.flag) System.out.printf(">>>>> %s = [%s] [%s]%n", prefix, data.trim(), data);
				if (!prefix.contains("phones") && !prefix.contains("taskIds")) {
					LnsSpliter lnsSpliter = LnsNodeTools.split(prefix);
					((ObjectNode) this.parentNode.at(lnsSpliter.getPathName())).put(lnsSpliter.getFieldName(), data.trim());
				}
			}
		}
	}
}
