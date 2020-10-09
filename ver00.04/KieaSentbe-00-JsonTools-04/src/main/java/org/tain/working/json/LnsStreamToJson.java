package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsStreamToJson {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private LnsMstInfo lnsMstInfo;
	
	private String streamData;
	private int offset = -1;
	
	private JsonNode infoNode;
	
	public LnsStreamToJson(LnsMstInfo lnsMstInfo, String streamData) {
		this.lnsMstInfo = lnsMstInfo;
		
		this.infoNode = (JsonNode) this.objectMapper.createObjectNode();
		((ObjectNode) this.infoNode).set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.infoNode).set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		if (Flag.flag) log.info(">>>>> LnsStreamToJson.infoNode = " + this.infoNode.toPrettyString());
		
		this.streamData = streamData;
	}
	
	public JsonNode get() {
		this.offset = 0;
		return traverse(this.infoNode, "");
	}
	
	public JsonNode traverse(JsonNode node, String prefix) {
		if (node.isObject()) {
			return traverseObject(node, prefix);
		} else if (node.isArray()) {
			return traverseArray(node, prefix);
		} else {
			throw new RuntimeException("Not yet implemented....");
		}
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
					int idxStart = this.offset;
					int idxEnd = this.offset + info.getLength();
					String data = this.streamData.substring(idxStart, idxEnd).trim();
					this.offset = idxEnd;
					
					if (!"".equals(data)) {
						switch (info.getType()) {
						case "STRING" : objectNode.put(fieldName, data); break;
						case "BOOLEAN": objectNode.put(fieldName, Boolean.valueOf(data)); break;
						case "INT"    : objectNode.put(fieldName, Integer.valueOf(data)); break;
						case "LONG"   : objectNode.put(fieldName, Long.valueOf(data)); break;
						case "DOUBLE" : objectNode.put(fieldName, Double.valueOf(data)); break;
						case "FLOAT"  : objectNode.put(fieldName, Float.valueOf(data)); break;
						default:
							objectNode.put(fieldName, data);
							break;
						}
					}
				}
			}
		});
		
		return (JsonNode) objectNode;
	}
	
	public JsonNode traverseArray(JsonNode node, String prefix) {
		ArrayNode arrayNode = this.objectMapper.createArrayNode();
		
		int arrSize = -1;
		if (Flag.flag) {
			String arrFieldName = prefix + "__arrSize";
			JsonNode arrFieldNameNode = this.lnsMstInfo.getBodyBaseInfoNode().path(arrFieldName);
			arrSize = arrFieldNameNode.asInt();
			if (Flag.flag) log.info(">>>>> [" + arrFieldName + "] = " + arrSize);
		}
		
		for (int index=0; index < arrSize; index++) {
			JsonNode itemNode = node.at("/0");
			
			if (traversable(itemNode)) {
				JsonNode retNode = traverse(itemNode, LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/"));
				if (!retNode.isEmpty())
					arrayNode.add(retNode);
			} else {
				LnsElementInfo info = new LnsElementInfo(itemNode.textValue());
				if (info.isUsable()) {
					int idxStart = this.offset;
					int idxEnd = this.offset + info.getLength();
					String data = this.streamData.substring(idxStart, idxEnd).trim();
					this.offset = idxEnd;
					
					if (!"".equals(data)) {
						switch (info.getType()) {
						case "STRING" : arrayNode.add(data); break;
						case "BOOLEAN": arrayNode.add(Boolean.valueOf(data)); break;
						case "INT"    : arrayNode.add(Integer.valueOf(data)); break;
						case "LONG"   : arrayNode.add(Long.valueOf(data)); break;
						case "DOUBLE" : arrayNode.add(Double.valueOf(data)); break;
						case "FLOAT"  : arrayNode.add(Float.valueOf(data)); break;
						default:
							arrayNode.add(data);
							break;
						}
					}
				}
			}
		}
		
		return (JsonNode) arrayNode;
	}
	
	public boolean traversable(JsonNode node) {
		return node.isObject() || node.isArray();
	}
}
