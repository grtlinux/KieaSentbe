package org.tain.mapper;

import java.io.File;

import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.StringTools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class LnsMstInfo implements Cloneable {

	////////////////////////////////////////////////////////////////////////
	
	private String basePath;
	private String fileName;
	private String filePath;
	
	////////////////////////////////////////////////////////////////////////
	
	private long lastModfied;
	
	private String strJsonInfo;
	
	private String reqResType;
	
	// InfoNode
	private JsonNode infoNode;
	
	// head
	private JsonNode headBaseInfoNode;
	private JsonNode headDataInfoNode;
	
	// body
	private JsonNode bodyBaseInfoNode;
	private JsonNode bodyDataInfoNode;
	
	////////////////////////////////////////////////////////////////////////
	
	public LnsMstInfo(String filePath) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			File file = new File(filePath);
			this.basePath = file.getParent();
			this.fileName = file.getName();
			this.filePath = this.basePath + File.separator + this.fileName;
		}
		
		this.updateInfo();
	}
	
	public LnsMstInfo(String basePath, String fileName) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			this.basePath = basePath;
			this.fileName = fileName;
			this.filePath = this.basePath + File.separator + this.fileName;
		}
		
		this.updateInfo();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	public void setLength(String strLength) {
		((ObjectNode) this.headBaseInfoNode).put("length", strLength);
	}
	
	public boolean checkAndUpdate(File entry) throws Exception {
		//log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (this.lastModfied < entry.lastModified()) {
				this.updateInfo();
				return true;
			}
		}
		
		return false;
	}
	
	public String getJsonHead() throws Exception {
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("length", this.headBaseInfoNode.get("length").asText());
			lnsJsonNode.put("reqres", this.reqResType.substring(0, 4));
			lnsJsonNode.put("type", this.reqResType.substring(4));
			lnsJsonNode.put("trNo", "999999");
			lnsJsonNode.put("reqDate", LnsNodeTools.getDate());
			lnsJsonNode.put("reqTime", LnsNodeTools.getTime());
			lnsJsonNode.put("resTime", "");
			lnsJsonNode.put("resCode", "");
			lnsJsonNode.put("resMessage", "");
			lnsJsonNode.put("reserved", "");
		}
		return lnsJsonNode.toPrettyString();
	}
	
	public String getStreamHead() throws Exception {
		StringBuffer sb = new StringBuffer();
		
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode(this.getJsonHead());
		}
		
		if (Flag.flag) {
			JsonNode node = lnsJsonNode.get();
			this.headDataInfoNode.fieldNames().forEachRemaining((String fieldName) -> {
				String strInfo = this.headDataInfoNode.get(fieldName).asText();
				LnsElementInfo info = new LnsElementInfo(strInfo);
				if (info.isUsable()) {
					String data = node.get(fieldName).asText();
					info.setFormat("%" + info.getLength() + "." + info.getLength() + "s");
					String fieldValue = String.format(info.getFormat(), data);
					sb.append(fieldValue);
				}
			});
		}
		
		return sb.toString();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	private void updateInfo() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			File file = new File(this.filePath);
			this.lastModfied = file.lastModified() + 2*1000;
		}
		
		if (Flag.flag) {
			this.strJsonInfo = StringTools.stringFromFile(this.filePath);
			this.infoNode = JsonPrint.getInstance().getObjectMapper().readTree(this.strJsonInfo);
			this.headBaseInfoNode = this.infoNode.at("/__head_base");
			this.headDataInfoNode = this.infoNode.at("/__head_data");
			this.bodyBaseInfoNode = this.infoNode.at("/__body_base");
			this.bodyDataInfoNode = this.infoNode.at("/__body_data");
		}
		
		if (Flag.flag) {
			StringBuffer sb = new StringBuffer();
			sb.append(this.infoNode.at("/__head_base").get("reqres").asText());
			sb.append(this.infoNode.at("/__head_base").get("type").asText());
			this.reqResType = sb.toString();
		}
		
		//if (Flag.flag) log.info(">>>>> updated.infoNode = {}", this.infoNode.toPrettyString());
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
}
