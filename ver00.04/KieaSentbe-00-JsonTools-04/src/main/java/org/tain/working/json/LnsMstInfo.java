package org.tain.working.json;

import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class LnsMstInfo implements Cloneable {

	////////////////////////////////////////////////////////////////////////
	
	private JsonNode headBaseInfoNode;
	private JsonNode headDataInfoNode;
	private JsonNode bodyBaseInfoNode;
	private JsonNode bodyDataInfoNode;
	
	////////////////////////////////////////////////////////////////////////
	
	public LnsMstInfo() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) setHeadInfo01();
		if (Flag.flag) setBodyInfo01();
	}
	
	////////////////////////////////////////////////////////////////////////
	
	private void setHeadInfo01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// common header
			String jsonHeadInfo = "{\n" + 
					"  \"__headBase\" : {\n" + 
					"    \"name\" : \"head\"\n" + 
					"  },\n" + 
					"  \"__headData\" : {\n" +
					"    \"length\" : \"L:4,T:string,D:0000\",\n" + 
					"    \"reqres\" : \"L:4,T:string,D:0700\",\n" + 
					"    \"type\" : \"L:3,T:string,D:100\",\n" + 
					"    \"trNo\" : \"L:6,T:string\",\n" + 
					"    \"reqDate\" : \"L:8,T:string\",\n" + 
					"    \"reqTime\" : \"L:6,T:string\",\n" + 
					"    \"resTime\" : \"L:6,T:string\",\n" + 
					"    \"resCode\" : \"L:3,T:string\",\n" + 
					"    \"resMessaage\" : \"L:20,T:string\",\n" + 
					"    \"reserved\" : \"L:40,T:string\",\n" + 
					"    \"dummy\" : \"L:-100,T:string\"\n" + 
					"  }\n" + 
					"}";
			JsonNode jsonNode = JsonPrint.getInstance().getObjectMapper().readTree(jsonHeadInfo);
			
			this.headBaseInfoNode = jsonNode.at("/__headBase");
			if (Flag.flag) log.info(">>>>> headBaseInfoNode:\n{}", this.headBaseInfoNode.toPrettyString());
			
			this.headDataInfoNode = jsonNode.at("/__headData");
			if (Flag.flag) log.info(">>>>> headDataInfoNode:\n{}", this.headDataInfoNode.toPrettyString());
		}
	}
	
	////////////////////////////////////////////////////////////////////////
	
	private void setBodyInfo01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// data info
			String jsonBodyInfo = "{\n" + 
					"  \"__bodyBase\" : {\n" + 
					"    \"name\" : \"Test.REQ.body\",\n" + 
					"    \"reqres\" : \"0700\",\n" + 
					"    \"type\" : \"100\",\n" + 
					"    \"/phones__arrSize\" : 5,\n" + 
					"    \"/taskIds__arrSize\" : 7\n" + 
					"  },\n" + 
					"  \"__bodyData\" : {\n" +
					"    \"name\" : \"L:20,T:string\",\n" + 
					"    \"salary\" : \"L:7,T:long\",\n" + 
					"    \"content\" : \"L:-100,T:string\",\n" + 
					"    \"phones\" : [ {\n" + 
					"        \"phoneType\" : \"L:5,T:string\",\n" + 
					"        \"phoneNumber\" : \"L:15,T:string\"\n" + 
					"      } "  +
					"    ],\n" + 
					"    \"taskIds\" : [ \"L:3,T:int\" ],\n" + 
					"    \"address\" : {\n" + 
					"      \"street\" : \"L:50,T:string\",\n" + 
					"      \"city\" : \"L:50,T:string\",\n" + 
					"      \"usable\" : \"L:5,T:boolean\"\n" + 
					"    }\n" + 
					"  }\n" + 
					"}";
			JsonNode jsonNode = JsonPrint.getInstance().getObjectMapper().readTree(jsonBodyInfo);
			
			this.bodyBaseInfoNode = jsonNode.at("/__bodyBase");
			if (Flag.flag) log.info(">>>>> bodyBaseInfoNode:\n{}", this.bodyBaseInfoNode.toPrettyString());
			
			this.bodyDataInfoNode = jsonNode.at("/__bodyData");
			if (Flag.flag) log.info(">>>>> bodyDataInfoNode:\n{}", this.bodyDataInfoNode.toPrettyString());
		}
	}
	
	////////////////////////////////////////////////////////////////////////
}
