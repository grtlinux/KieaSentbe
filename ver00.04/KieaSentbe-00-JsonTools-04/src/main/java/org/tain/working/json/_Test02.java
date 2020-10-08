package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class _Test02 {

	private ObjectMapper objectMapper;
	
	private LnsMstInfo lnsMstInfo;
	private JsonNode bodyBaseInfoNode;
	
	private String streamData;
	private int offset = 0;
	
	private JsonNode dataNode;
	
	public _Test02(LnsMstInfo lnsMstInfo, String streamData) {
		this.objectMapper = new ObjectMapper();
		
		this.lnsMstInfo = lnsMstInfo;
		this.streamData = streamData;
		
		this.bodyBaseInfoNode = this.lnsMstInfo.getBodyBaseInfoNode();
		
		this.dataNode = (JsonNode) new ObjectMapper().createObjectNode();
		((ObjectNode) this.dataNode).set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.dataNode).set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		if (Flag.flag) System.out.println(">>>>> rootNode = " + this.dataNode.toPrettyString());
	}
	
	public JsonNode get() {
		this.offset = 0;
		return traverse(this.dataNode, "");
	}
	
	public JsonNode traverse(JsonNode node, String prefix) {
		JsonNode retNode = null;
		if (node.isObject()) {
			retNode = traverseObject(node, prefix);
		} else if (node.isArray()) {
			retNode = traverseArray(node, prefix);
		} else {
			throw new RuntimeException("Not yet implemented....");
		}
		return retNode;
	}
	
	public JsonNode traverseObject(JsonNode node, String prefix) {
		ObjectNode objectNode = this.objectMapper.createObjectNode();
		
		node.fieldNames().forEachRemaining((String fieldName) -> {
			JsonNode childNode = node.get(fieldName);
			if (traversable(childNode)) {
				JsonNode retNode = traverse(childNode, LnsNodeTools.getPrefix(prefix, fieldName, "/"));
				objectNode.set(fieldName, retNode);
			} else {
				LnsElementInfo info = new LnsElementInfo(childNode.textValue());
				if (info.isUsable()) {
					String data = this.streamData.substring(this.offset, this.offset + info.getLength()).trim();
					this.offset += info.getLength();
					
					if (!"".equals(data))
						objectNode.put(fieldName, data);
				}
			}
		});
		
		return (JsonNode) objectNode;
	}
	
	public JsonNode traverseArray(JsonNode node, String prefix) {
		ArrayNode arrayNode = this.objectMapper.createArrayNode();
		
		if (Flag.flag) {
			String arrFieldName = prefix + "__arrSize";
			JsonNode arrFieldNameNode = this.bodyBaseInfoNode.path(arrFieldName);
			int arrSize = arrFieldNameNode.asInt();
			if (Flag.flag) System.out.println(">>>>> [" + arrFieldName + "] = " + arrSize);
		}
		
		for (int index=0; index < 5; index++) {
			
			JsonNode itemNode = node.at("/0");
			
			if (traversable(itemNode)) {
				JsonNode retNode = traverse(itemNode, LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/"));
				if (!retNode.isEmpty())
					arrayNode.add(retNode);
			} else {
				LnsElementInfo info = new LnsElementInfo(itemNode.textValue());
				if (info.isUsable()) {
					String data = this.streamData.substring(this.offset, this.offset + info.getLength()).trim();
					this.offset += info.getLength();
					
					if (!"".equals(data))
						arrayNode.add(data);
				}
			}
		}
		
		return (JsonNode) arrayNode;
	}
	
	public boolean traversable(JsonNode node) {
		return node.isObject() || node.isArray();
	}
}
