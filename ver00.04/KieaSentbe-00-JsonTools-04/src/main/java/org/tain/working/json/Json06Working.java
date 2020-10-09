package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;

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
			if (Flag.flag) log.info(">>>>> lnsMstInfo.structLength = {}", new LnsStreamLength(lnsMstInfo).getLength());
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
				sb.append(String.format("%04d", lenStream - 4));
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
}
