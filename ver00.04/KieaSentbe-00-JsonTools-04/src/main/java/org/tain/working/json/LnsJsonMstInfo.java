package org.tain.working.json;

import java.time.LocalDateTime;

import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsJsonMstInfo implements Cloneable {

	////////////////////////////////////////////////////////////////////////
	
	@Override
	public LnsJsonMstInfo clone() throws CloneNotSupportedException {
		return (LnsJsonMstInfo) super.clone();
	}
	
	public String toString() {
		return localDateTime.toString();
	}
	
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	
	private LocalDateTime localDateTime = null;
	
	public LnsJsonMstInfo() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		this.localDateTime = LocalDateTime.now();
		
		if (Flag.flag) base01();
		if (Flag.flag) input01();
	}
	
	private JsonNode mstInfoNode = null;
	private JsonNode jsonInfoNode = null;
	private JsonNode jsonInputNode = null;
	
	private void base01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String jsonInfo = "{\n" + 
					"  \"__mstInfo\" : {\n" + 
					"    \"name\" : \"helloMstInfo\",\n" + 
					"    \"/phones__arrSize\" : 5,\n" + 
					"    \"/taskIds__arrSize\" : 7\n" + 
					"  },\n" + 
					"  \"__jsonInfo\" : {\n" +
					"    \"name\" : \"L:20,T:string\",\n" + 
					"    \"salary\" : \"L:20,T:long\",\n" + 
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
			JsonNode jsonNode = JsonPrint.getInstance().getObjectMapper().readTree(jsonInfo);
			this.mstInfoNode = jsonNode.at("/__mstInfo");
			this.jsonInfoNode = jsonNode.at("/__jsonInfo");
			if (Flag.flag) System.out.println(">>>>> mstInfoNode = " + this.mstInfoNode.toPrettyString());
			if (Flag.flag) System.out.println(">>>>> jsonInfoNode = " + this.jsonInfoNode.toPrettyString());
		}
	}
	
	private void input01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String jsonInput = "{\n" +
					"  \"name\" : \"Jake\",\n" + 
					"  \"salary\" : 3000.5,\n" + 
					"  \"phones\" : [ {\n" + 
					"      \"phoneType\" : \"cell\",\n" + 
					"      \"phoneNumber\" : \"111-111-111\"\n" + 
					"    }, {\n" + 
					"      \"phoneType\" : \"work\",\n" + 
					"      \"phoneNumber\" : \"222-222-222\"\n" + 
					"    } "  +
					"  ],\n" + 
					"  \"taskIds\" : [ 11, 22.2, 33000 ],\n" + 
					"  \"address\" : {\n" + 
					"    \"street\" : \"101 Blue Dr\",\n" + 
					"    \"city\" : \"White Smoke\",\n" + 
					"    \"usable\" : true\n" + 
					"  }\n" + 
					"}";
			this.jsonInputNode = JsonPrint.getInstance().getObjectMapper().readTree(jsonInput);
			if (Flag.flag) System.out.println(">>>>> jsonInputNode = " + this.jsonInputNode.toPrettyString());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public String getMstInfo() {
		return this.mstInfoNode.toPrettyString();
	}
	public String getJsonInfo() {
		return this.jsonInfoNode.toPrettyString();
	}
	public String getJsonInput() {
		return this.jsonInputNode.toPrettyString();
	}
	
	public JsonNode getMstInfoNode() {
		return this.mstInfoNode;
	}
	public JsonNode getJsonInfoNode() {
		return this.jsonInfoNode;
	}
	public JsonNode getJsonInputNode() {
		return this.jsonInputNode;
	}
}
