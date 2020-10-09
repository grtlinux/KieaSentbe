package org.tain.working.json;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsStreamLength {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private LnsMstInfo lnsMstInfo;
	private int length = -1;
	
	private JsonNode infoNode;
	
	public LnsStreamLength(LnsMstInfo lnsMstInfo) {
		this.lnsMstInfo = lnsMstInfo;
		
		this.infoNode = (JsonNode) this.objectMapper.createObjectNode();
		((ObjectNode) this.infoNode).set("__head", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.infoNode).set("__body", this.lnsMstInfo.getBodyDataInfoNode());
		if (Flag.flag) log.info(">>>>> LnsStreamLength.infoNode = " + this.infoNode.toPrettyString());
	}
	
	public String getStrLength() {
		return String.format("%04d", this.getLength());
	}
	
	public int getLength() {
		this.length = 0;
		traverse(this.infoNode, "");
		return length - 4;
	}
	
	private void traverse(JsonNode node, String prefix) {
		if (Flag.flag) {
			if (node.isObject()) {
				traverseObject(node, prefix);
			} else if (node.isArray()) {
				traverseArray(node, prefix);
			} else {
				throw new RuntimeException("Not yet implemented...");
			}
		}
	}
	
	private void traverseObject(JsonNode node, String prefix) {
		if (Flag.flag) {
			node.fieldNames().forEachRemaining((String fieldName) -> {
				String _prefix = LnsNodeTools.getPrefix(prefix, fieldName, "/");
				
				JsonNode childNode = node.get(fieldName);
				if (traversable(childNode)) {
					traverse(childNode, _prefix);
				} else {
					String strInfo = childNode.textValue();
					LnsElementInfo info = new LnsElementInfo(strInfo);
					if (info.isUsable()) {
						this.length += info.getLength();
					}
				}
			});
		}
	}
	
	private void traverseArray(JsonNode node, String prefix) {
		int arrSize = -1;
		if (Flag.flag) {
			String arrFieldName = prefix + "__arrSize";
			JsonNode arrFieldNameNode = this.lnsMstInfo.getBodyBaseInfoNode().path(arrFieldName);
			arrSize = arrFieldNameNode.asInt();
			if (Flag.flag) log.info(">>>>> {} = {}", arrFieldName, arrSize);
		}
		
		if (Flag.flag) {
			for (int index = 0; index < arrSize; index ++) {
				String _prefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/");
				
				JsonNode itemNode = node.at("/0");
				if (traversable(itemNode)) {
					traverse(itemNode, _prefix);
				} else {
					String strInfo = itemNode.textValue();
					LnsElementInfo info = new LnsElementInfo(strInfo);
					if (info.isUsable()) {
						this.length += info.getLength();
					}
				}
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		return node.isObject() || node.isArray();
	}
}
