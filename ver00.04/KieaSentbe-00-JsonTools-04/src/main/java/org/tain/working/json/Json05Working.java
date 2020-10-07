package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json05Working {

	/*
	 * test01()
	 */
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// print body data
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			JsonNode bodyDataInfoNode = lnsMstInfo.getBodyDataInfoNode();
			System.out.println(">>>>> \n" + bodyDataInfoNode.toPrettyString());
		}
		
		if (Flag.flag) {
			// print CStruct
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			
			System.out.println(">>>>> HeadCStruct \n" + new CStruct(lnsMstInfo).getHeadCStruct());
			System.out.println(">>>>> BodyCStruct \n" + new CStruct(lnsMstInfo).getBodyCStruct());
			System.out.println(">>>>> CStruct \n" + new CStruct(lnsMstInfo).get());
		}
		
		String streamData = null;
		if (Flag.flag) {
			String jsonData = "{\n" + 
					"  \"__head\" : {\n" +
					"    \"length\" : \"0000\",\n" + 
					"    \"reqres\" : \"0700\",\n" + 
					"    \"type\" : \"100\",\n" + 
					"    \"trNo\" : \"1\",\n" + 
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
		
		String jsonData = null;
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			//jsonData = new LnsStreamToJson(lnsMstInfo, streamData).get();
			jsonData = new _Test01(lnsMstInfo, streamData).get();
			
			System.out.println(">>>>> JsonData = " + jsonData);
		}
	}
}
