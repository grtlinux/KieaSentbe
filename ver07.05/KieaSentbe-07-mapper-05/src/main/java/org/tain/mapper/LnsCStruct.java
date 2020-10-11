package org.tain.mapper;

import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsCStruct {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private LnsMstInfo lnsMstInfo;
	
	private JsonNode infoNode;
	private StringBuffer sb = new StringBuffer();
	
	public LnsCStruct(LnsMstInfo lnsMstInfo) {
		this.lnsMstInfo = lnsMstInfo;
		
		this.infoNode = (JsonNode) this.objectMapper.createObjectNode();
		((ObjectNode) this.infoNode).set("__head_data", this.lnsMstInfo.getHeadDataInfoNode());
		((ObjectNode) this.infoNode).set("__body_data", this.lnsMstInfo.getBodyDataInfoNode());
		if (!Flag.flag) log.info(">>>>> LnsCStruct.infoNode = " + this.infoNode.toPrettyString());
	}
	
	public String get() {
		traverse(this.infoNode, "");
		return sb.toString();
	}
	
	private void traverse(JsonNode node, String prefix) {
		if (Flag.flag) {
			if (node.isObject()) {
				traverseObject(node, prefix);
			} else if (node.isArray()) {
				traverseArray(node, prefix);
			} else {
				throw new RuntimeException("Not yet implemented... [by Kiea Seok Kang]");
			}
		}
	}
	
	private void traverseObject(JsonNode node, String prefix) {
		if (Flag.flag) {
			node.fieldNames().forEachRemaining((String fieldName) -> {
				String _prefix = LnsNodeTools.getPrefix(prefix, fieldName, "/");
				JsonNode childNode = node.get(fieldName);
				processNode(childNode, fieldName, _prefix);
				if (traversable(childNode)) {
					traverse(childNode, _prefix);
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
			for (int index=0; index < arrSize; index++) {
				String _prefix = LnsNodeTools.getPrefix(prefix, String.valueOf(index), "/");
				JsonNode itemNode = node.at("/0");
				processNode(itemNode, "arrayElements", _prefix);
				if (traversable(itemNode)) {
					traverse(itemNode, _prefix);
				}
			}
		}
	}
	
	private boolean traversable(JsonNode node) {
		return node.isObject() || node.isArray();
	}
	
	private void processNode(JsonNode node, String keyName, String prefix) {
		String line = null;
		if (traversable(node)) {
			line = String.format("%-30s   %s(%s)%n", prefix, keyName, node.getNodeType());
			//if (Flag.flag) System.out.print(">>>>> " + line);
			//sb.append(line);
		} else {
			String strInfo = node.textValue();
			LnsElementInfo info = new LnsElementInfo(strInfo);
			if (info.isUsable()) {
				if (prefix.contains("/__head_data/")) {
					prefix = "head_" + prefix.substring(13).replace('/', '_');
				} else if (prefix.contains("/__body_data/")) {
					prefix = prefix.substring(13).replace('/', '_');
				}
				line = String.format("char %-30s   [%3d]; /* %s */%n", prefix, info.getLength(), info.getComment());
				sb.append(line);
			}
		}
	}
}
