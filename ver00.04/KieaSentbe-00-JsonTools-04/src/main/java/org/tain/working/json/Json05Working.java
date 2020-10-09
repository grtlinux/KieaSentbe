package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json05Working {

	/*
	 * test01()
	 */
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (!Flag.flag) {
			// print body data
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			JsonNode bodyDataInfoNode = lnsMstInfo.getBodyDataInfoNode();
			System.out.println(">>>>> \n" + bodyDataInfoNode.toPrettyString());
		}
		
		if (!Flag.flag) {
			// print CStruct
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			
			System.out.println(">>>>> HeadCStruct \n" + new LnsCStruct(lnsMstInfo).getHeadCStruct());
			System.out.println(">>>>> BodyCStruct \n" + new LnsCStruct(lnsMstInfo).getBodyCStruct());
			System.out.println(">>>>> CStruct \n" + new LnsCStruct(lnsMstInfo).get());
		}
		
		String streamData = null;
		if (Flag.flag) {
			String jsonData = "{\n" + 
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
			
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			//System.out.println(">>>>> HeadStream \n" + new JsonToStream(lnsMstInfo, jsonData).getHeadStream());
			//System.out.println(">>>>> BodyStream \n" + new JsonToStream(lnsMstInfo, jsonData).getBodyStream());
			streamData = new LnsJsonToStream(lnsMstInfo, jsonData).get();
			System.out.println(">>>>> Stream \n(" + streamData.length() + ") [" + streamData + "]");
		}
		
		if (!Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			//jsonData = new LnsStreamToJson(lnsMstInfo, streamData).get();
			String jsonData = new _Test01(lnsMstInfo, streamData).get();
			if (Flag.flag) System.out.println(">>>>> JsonData = " + jsonData);
		}
		
		if (!Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			
			JsonNode dataNode = (JsonNode) new ObjectMapper().createObjectNode();
			((ObjectNode) dataNode).set("__head", lnsMstInfo.getHeadDataInfoNode());
			((ObjectNode) dataNode).set("__body", lnsMstInfo.getBodyDataInfoNode());
			if (Flag.flag) System.out.println(">>>>> dataNode = " + dataNode.toPrettyString());
			
			JsonNode findValue = dataNode.findValue("address");
			if (Flag.flag) System.out.println(">>>>> findValue = " + findValue.toPrettyString());
			
			JsonNode findParent = dataNode.findParent("address");
			if (Flag.flag) System.out.println(">>>>> findParent = " + findParent.toPrettyString());
			
			JsonNode findPath = dataNode.findPath("address");
			if (Flag.flag) System.out.println(">>>>> findPath = " + findPath.toPrettyString());
		}
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			JsonNode retNode = new _Test02(lnsMstInfo, streamData).get();
			if (Flag.flag) System.out.println(">>>>> retNode = " + retNode.toPrettyString());
		}
	}
}
