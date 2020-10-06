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
		
		if (Flag.flag) {
			String jsonData = "{\n" + 
					"  \"__head\" : {\n" +
					"    \"length\" : \"0000\",\n" + 
					"    \"reqres\" : \"0700\",\n" + 
					"    \"type\" : \"100\",\n" + 
					"    \"code\" : \"\",\n" + 
					"    \"messaage\" : \"\",\n" + 
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
			System.out.println(">>>>> Stream \n" + new JsonToStream(lnsMstInfo, jsonData).get());
		}
	}
}
