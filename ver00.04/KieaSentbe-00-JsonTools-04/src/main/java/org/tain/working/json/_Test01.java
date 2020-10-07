package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SuppressWarnings("unused")
public class _Test01 {

	private LnsMstInfo lnsMstInfo;
	
	private String streamData;
	private int offset = 0;
	
	private JsonNode dataNode;
	private ObjectNode returnNode;
	
	public _Test01(LnsMstInfo lnsMstInfo, String streamData) {
		this.lnsMstInfo = lnsMstInfo;
		this.streamData = streamData;
		
		this.dataNode = (JsonNode) new ObjectMapper().createObjectNode();
		((ObjectNode) this.dataNode).set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.dataNode).set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		if (Flag.flag) System.out.println(">>>>> rootNode = " + this.dataNode.toPrettyString());
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		this.offset = 0;
		String prefix = "";
		traverse(sb, this.dataNode, prefix);
		
		return sb.toString();
	}
	
	private void traverse(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			if (node.getNodeType() == JsonNodeType.OBJECT) {
				traverseObject(sb, node, prefix);
			} else if (node.getNodeType() == JsonNodeType.ARRAY) {
				traverseArray(sb, node, prefix);
			} else {
				throw new RuntimeException("Not yet implemented....");
			}
		}
	}
	
	private void traverseObject(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			node.fieldNames().forEachRemaining((String fieldName) -> {
				String _prefix = LnsNodeTools.getPrefix(prefix, fieldName, "/");
				
				JsonNode childNode = node.get(fieldName);
				processNode(sb, childNode, fieldName, _prefix);
				if (traversable(childNode)) {
					traverse(sb, childNode, _prefix);
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
				String _prefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/");
				
				JsonNode itemNode = node.at("/" + index);
				if (itemNode.isEmpty()) {
					itemNode = node.at("/0");
				}
				processNode(sb, itemNode, "arrayElements", _prefix);
				if (traversable(itemNode)) {
					traverse(sb, itemNode, _prefix);
				}
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		if (Flag.flag) {
			return isObjectNode(node) || isArrayNode(node);
		}
		return true;
	}
	
	private boolean isObjectNode(JsonNode node) {
		return node.getNodeType() == JsonNodeType.OBJECT;
	}
	
	private boolean isArrayNode(JsonNode node) {
		return node.getNodeType() == JsonNodeType.ARRAY;
	}
	
	private void processNode(StringBuffer sb, JsonNode node, String keyName, String prefix) {
		if (Flag.flag) {
			if (isObjectNode(node)) {
				// create ObjectNode
			} else if (isArrayNode(node)) {
				// create ArrayNode
			} else {
				Object value = null;
				if (node.isTextual()) {
					value = node.textValue();
				} else if (node.isNumber()) {
					value = node.numberValue();
				} else if (node.isBoolean()) {
					value = node.booleanValue();
				} else {
					value = "<< NULL >>";
				}
				
				if (!Flag.flag) {
					String line = String.format("%-30s   %s(%s) = %s%n", prefix, keyName, node.getNodeType(), value);
					if (Flag.flag) System.out.print(">>>>> " + line);
				}
				
				if (!Flag.flag) {
					LnsElementInfo info = new LnsElementInfo(String.valueOf(value));
					if (info.isUsable()) {
						String data = this.streamData.substring(this.offset, this.offset + info.getLength());
						this.offset += info.getLength();
						
						String line = String.format("%-30s   %s(%s) = %s, %s%n", prefix, keyName, node.getNodeType(), value, data.trim());
						if (Flag.flag) System.out.print(">>>>> " + line);
					}
				}
				
				if (Flag.flag) {
					LnsElementInfo info = new LnsElementInfo(String.valueOf(value));
					if (info.isUsable()) {
						String data = this.streamData.substring(this.offset, this.offset + info.getLength());
						this.offset += info.getLength();
						
						String line = String.format("%-30s   %s(%s) = %s, %s%n", prefix, keyName, node.getNodeType(), value, data.trim());
						if (Flag.flag) System.out.print(">>>>> " + line);
					}
				}
				
				if (Flag.flag) {
					/*
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
					*/
				}
			}
		}
	}
}
