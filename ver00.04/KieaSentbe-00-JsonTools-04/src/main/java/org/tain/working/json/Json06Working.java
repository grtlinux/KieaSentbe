package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json06Working {

	private String strJson = "{\n" + 
			"  \"__head\" : {\n" +
			"    \"length\" : \"0000\",\n" + 
			"    \"reqres\" : \"0700\",\n" + 
			"    \"type\" : \"100\",\n" + 
			"    \"trNo\" : \"000001\",\n" + 
			"    \"reqDate\" : \"20201005\",\n" + 
			"    \"reqTime\" : \"121212\",\n" + 
			"    \"resTime\" : \"121313\",\n" + 
			"    \"resCode\" : \"\",\n" + 
			"    \"resMessaage\" : \"\",\n" + 
			"    \"reserved\" : \"\"\n" + 
			"  },\n" + 
			"  \"__body\" : {\n" +
			"    \"name\" : \"Jake\",\n" + 
			"    \"salary\" : 3000,\n" + 
			"    \"phones\" : [ {\n" + 
			"        \"phoneType\" : \"cell\",\n" + 
			"        \"phoneNumber\" : \"111-111-111\"\n" + 
			"      }, {\n" + 
			"        \"phoneType\" : \"work\",\n" + 
			"        \"phoneNumber\" : \"222-222-222\"\n" + 
			"      } "  +
			"    ],\n" + 
			"    \"taskIds\" : [ 11, 22, 33 ],\n" + 
			"    \"address\" : {\n" + 
			"      \"street\" : \"101 Blue Dr\",\n" + 
			"      \"city\" : \"White Smoke\",\n" + 
			"      \"usable\" : true\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	private String strStream = null;
	private JsonNode dataNode = null;
	
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			
			if (Flag.flag) log.info(">>>>> lnsMstInfo.headBaseInfoNode = {}", lnsMstInfo.getHeadBaseInfoNode().toPrettyString());
			if (Flag.flag) log.info(">>>>> lnsMstInfo.headDataInfoNode = {}", lnsMstInfo.getHeadDataInfoNode().toPrettyString());
			if (Flag.flag) log.info(">>>>> lnsMstInfo.bodyBaseInfoNode = {}", lnsMstInfo.getBodyBaseInfoNode().toPrettyString());
			if (Flag.flag) log.info(">>>>> lnsMstInfo.bodyDataInfoNode = {}", lnsMstInfo.getBodyDataInfoNode().toPrettyString());
			if (Flag.flag) log.info(">>>>> lnsMstInfo.structLength = {}", new LnsStreamLength(lnsMstInfo).getStrLength());
		}
	}
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			String strCStruct = new LnsCStruct(lnsMstInfo).get();
			if (Flag.flag) log.info(">>>>> CStruct \n{}", strCStruct);
		}
	}
	
	public void test03() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			this.strStream = new LnsJsonToStream(lnsMstInfo, this.strJson).get();
			int lenStream = this.strStream.length();
			if (Flag.flag) {
				StringBuffer sb = new StringBuffer();
				sb.append(String.format("%s", new LnsStreamLength(lnsMstInfo).getStrLength()));
				sb.append(this.strStream.substring(4));
				this.strStream = sb.toString();
			}
			if (Flag.flag) log.info(">>>>> Stream \n(length = {}) [{}]", lenStream, this.strStream);
		}
	}
	
	public void test04() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			this.dataNode = new LnsStreamToJson(lnsMstInfo, strStream).get();
			if (Flag.flag) log.info(">>>>> dataNode = {}", this.dataNode.toPrettyString());
		}
	}
	
	public void test05() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			((ObjectNode) this.dataNode.at("/__body/address")).put("city", "Hello, world city....");
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-1 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ArrayNode) this.dataNode.at("/__body/taskIds")).add(99);
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-2 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ObjectNode) this.dataNode.path("__body").path("address")).put("city", "Yangchung-gu shinjung-dong.");
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-3 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ArrayNode) this.dataNode.at("/__body/phones")).add(LnsNodeTools.getJsonNode(
					"{\n" + 
					"      \"phoneType\" : \"home\",\n" + 
					"      \"phoneNumber\" : \"333-333-333\"\n" + 
					"    }"));
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-4 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ObjectNode) this.dataNode.path("__body").path("address")).put("nation", "Korea");
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-5 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ObjectNode) this.dataNode.path("__body").path("address")).set("family", LnsNodeTools.getJsonNode(
					"{\n" + 
					"      \"father\" : true,\n" + 
					"      \"mother\" : true,\n" + 
					"      \"sister\" : true,\n" + 
					"      \"brother\" : false\n" + 
					"    }"));
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-6 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ObjectNode) this.dataNode.path("__head")).put("reqDate", LnsNodeTools.getDate());
			((ObjectNode) this.dataNode.path("__head")).put("reqTime", LnsNodeTools.getTime());
			((ObjectNode) this.dataNode.path("__head")).put("resTime", LnsNodeTools.getTime());
			((ObjectNode) this.dataNode.path("__head")).put("resCode", "000");
			((ObjectNode) this.dataNode.path("__head")).put("resMessage", "SUCCESS");
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-7 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			((ArrayNode) this.dataNode.at("/__body/taskIds")).remove(2);
			((ArrayNode) this.dataNode.path("__body").path("phones")).remove(0);
			((ObjectNode) this.dataNode.path("__body").path("address")).remove("family");
			if (Flag.flag) log.info(">>>>> dataNode.UPDATE-8 = {}", this.dataNode.toPrettyString());
		}
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			String strStream = new LnsJsonToStream(lnsMstInfo, this.dataNode).get();
			if (Flag.flag) log.info(">>>>> strStream = {}", strStream);
		}
	}
}
