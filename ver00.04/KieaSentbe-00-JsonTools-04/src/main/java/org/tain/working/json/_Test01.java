package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class _Test01 {

	private LnsMstInfo lnsMstInfo;
	
	private String streamData;
	private int offset = 0;
	
	private JsonNode rootNode;
	private ObjectNode returnNode;
	
	public _Test01(LnsMstInfo lnsMstInfo, String streamData) {
		this.lnsMstInfo = lnsMstInfo;
		this.streamData = streamData;
		
		this.rootNode = (JsonNode) new ObjectMapper().createObjectNode();
		((ObjectNode) this.rootNode).set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.rootNode).set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		if (Flag.flag) System.out.println(">>>>> rootNode = " + this.rootNode.toPrettyString());
	}
	
	public String get() {
		StringBuffer sb = new StringBuffer();
		
		String prefix = "";
		
		traverse(sb, this.rootNode, prefix);
		
		return sb.toString();
	}
	
	private void traverse(StringBuffer sb, JsonNode node, String prefix) {
		if (Flag.flag) {
			if (node.getNodeType() == JsonNodeType.OBJECT) {
				traverseObject(sb, node, prefix);
			} else if (node.getNodeType() == JsonNodeType.ARRAY) {
				traverseArray(sb, node, prefix);
			} else {
				throw new RuntimeException("Not yet implemented...");
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
		if (Flag.flag) {
			boolean isObject = node.getNodeType() == JsonNodeType.OBJECT;
			boolean isArray = node.getNodeType() == JsonNodeType.ARRAY;
			return isObject || isArray;
		}
		return false;
	}
	
	private void processNode(StringBuffer sb, JsonNode node, String keyName, String prefix) {
		if (traverse)
	}
}
