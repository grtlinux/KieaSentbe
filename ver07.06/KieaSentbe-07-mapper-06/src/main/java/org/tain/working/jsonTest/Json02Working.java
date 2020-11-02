package org.tain.working.jsonTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsCStruct;
import org.tain.mapper.LnsJsonToStream;
import org.tain.mapper.LnsMstInfo;
import org.tain.mapper.LnsStreamLength;
import org.tain.mapper.LnsStreamToJson;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json02Working {

	///////////////////////////////////////////////////////////////////////////
	
	private String strJsonMstInfo01 = "{\n" + 
			"    \"__headBase\" : {\n" + 
			"        \"name\" : \"Test.REQ.head\"\n" + 
			"    },\n" + 
			"    \"__headData\" : {\n" + 
			"        \"length\" : \"L:4,T:string,D:0000,C:길이\",\n" + 
			"        \"reqres\" : \"L:4,T:string,D:0700,C:송수신\",\n" + 
			"        \"type\" : \"L:3,T:string,D:100,C:전문명\",\n" + 
			"        \"trNo\" : \"L:6,T:string,C:TR번호\",\n" + 
			"        \"reqDate\" : \"L:8,T:string,C:요청일자\",\n" + 
			"        \"reqTime\" : \"L:6,T:string,C:요청시간\",\n" + 
			"        \"resTime\" : \"L:6,T:string,C:응답시간\",\n" + 
			"        \"resCode\" : \"L:3,T:string,C:응답코드\",\n" + 
			"        \"resMessage\" : \"L:20,T:string,C:응답메시지\",\n" + 
			"        \"reserved\" : \"L:40,T:string,C:예약공간\",\n" + 
			"        \"dummy\" : \"L:-100,T:string,C:안쓴다\"\n" + 
			"    },\n" + 
			"    \"__bodyBase\" : {\n" + 
			"        \"name\" : \"Test.REQ.body\",\n" + 
			"        \"reqres\" : \"0700\",\n" + 
			"        \"type\" : \"100\",\n" + 
			"        \"/__body/phones__arrSize\" : 5,\n" + 
			"        \"/__body/taskIds__arrSize\" : 7\n" + 
			"    },\n" + 
			"    \"__bodyData\" : {\n" + 
			"        \"name\" : \"L:20,T:string\",\n" + 
			"        \"salary\" : \"L:7,T:long\",\n" + 
			"        \"content\" : \"L:-100,T:string\",\n" + 
			"        \"phones\" : [ {\n" + 
			"            \"phoneType\" : \"L:5,T:string\",\n" + 
			"            \"phoneNumber\" : \"L:15,T:string\"\n" + 
			"        } ],\n" + 
			"        \"taskIds\" : [ \"L:3,T:int\" ],\n" + 
			"        \"address\" : {\n" + 
			"            \"street\" : \"L:50,T:string\",\n" + 
			"            \"city\" : \"L:50,T:string\",\n" + 
			"            \"usable\" : \"L:5,T:boolean\"\n" + 
			"        }\n" + 
			"    }\n" + 
			"}";
	
	private String strJsonMstInfo10 = "";
	
	///////////////////////////////////////////////////////////////////////////
	
	private String strJsonData01 = "{\n" + 
			"  \"__head_data\" : {\n" + 
			"    \"length\" : \"0000\",\n" + 
			"    \"reqres\" : \"1000\",\n" + 
			"    \"type\" : \"000\",\n" + 
			"    \"trNo\" : \"000001\",\n" + 
			"    \"reqDate\" : \"20201011\",\n" + 
			"    \"reqTime\" : \"172820\",\n" + 
			"    \"resTime\" : \"172820\",\n" + 
			"    \"resCode\" : \"000\",\n" + 
			"    \"resMessage\" : \"SUCCESS\"\n" + 
			"  },\n" + 
			"  \"__body_data\" : {\n" + 
			"    \"name\" : \"Jake\",\n" + 
			"    \"salary\" : 3000,\n" + 
			"    \"phones\" : [ {\n" + 
			"      \"phoneType\" : \"work\",\n" + 
			"      \"phoneNumber\" : \"222-222-222\"\n" + 
			"    }, {\n" + 
			"      \"phoneType\" : \"home\",\n" + 
			"      \"phoneNumber\" : \"333-333-333\"\n" + 
			"    } ],\n" + 
			"    \"taskIds\" : [ 11, 22, 99 ],\n" + 
			"    \"address\" : {\n" + 
			"      \"street\" : \"101 Blue Dr\",\n" + 
			"      \"city\" : \"Yangchung-gu shinjung-dong.\",\n" + 
			"      \"usable\" : true,\n" + 
			"      \"nation\" : \"Korea\"\n" + 
			"    }\n" + 
			"  }\n" + 
			"}";
	
	private String strJsonData10 = "{\n" + 
			"  \"agreements\": {\n" + 
			"    \"version\": 1,\n" + 
			"    \"list\": [\n" + 
			"      \"SSFCTS\",\n" + 
			"      \"EFTS\",\n" + 
			"      \"PRIVACY\",\n" + 
			"      \"CAUTIONS\",\n" + 
			"      \"CLAIMS\"\n" + 
			"    ]\n" + 
			"  },\n" + 
			"  \"phone\": {\n" + 
			"    \"iso\": \"KR\",\n" + 
			"    \"number\": \"1012340001\"\n" + 
			"  },\n" + 
			"  \"user_name\": {\n" + 
			"    \"first\": \"Jiwon\",\n" + 
			"    \"middle\": \"\",\n" + 
			"    \"last\": \"Tak\"\n" + 
			"  },\n" + 
			"  \"gender\": \"1\",\n" + 
			"  \"account_number\": \"05012345670001\",\n" + 
			"  \"account_holder_name\": \"Jiwon Tak\",\n" + 
			"  \"birth_date\": \"19701225\",\n" + 
			"  \"id_number\": \"KR\",\n" + 
			"  \"nationality_iso\": \"701225-1230001\",\n" + 
			"  \"id_type\": \"1\",\n" + 
			"  \"email\": \"email0001@sentbe.com\",\n" + 
			"  \"often_send_country_iso\": \"PH\",\n" + 
			"  \"occupation\": \"3\",\n" + 
			"  \"funds_source\": \"3\",\n" + 
			"  \"transfer_purpose\": \"3\"\n" + 
			"}";
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) System.out.println(">>>>>>>>>> strJsonMstInfo01 =" + strJsonMstInfo01);
			if (Flag.flag) System.out.println(">>>>>>>>>> strJsonMstInfo10 = " + strJsonMstInfo10);
			if (Flag.flag) System.out.println(">>>>>>>>>> strJsonData01 = " + strJsonData01);
			if (Flag.flag) System.out.println(">>>>>>>>>> strJsonData10 = " + strJsonData10);
		}
		
		String basePath = null;
		if (Flag.flag) {
			String home = this.projEnvParamProperties.getHome();
			String base = this.projEnvParamProperties.getBase();
			basePath = home + base;
			if (Flag.flag) System.out.println("BasePath name is " + basePath);
		}
		
		Map<String, LnsMstInfo> mapInfo = new HashMap<>();
		if (Flag.flag) {
			File fileBasePath = new File(basePath);
			for (final File fileEntry : fileBasePath.listFiles()) {
				if (fileEntry.isFile()) {
					if (Flag.flag) System.out.printf("[%s] [%s]%n", fileEntry.getParent(), fileEntry.getName());
					
					LnsMstInfo lnsMstInfo = new LnsMstInfo(fileEntry.getParent(), fileEntry.getName());
					String reqResType = lnsMstInfo.getReqResType();
					String fileName = lnsMstInfo.getFileName();
					mapInfo.put(reqResType, lnsMstInfo);
					mapInfo.put(fileName, lnsMstInfo);
					if (Flag.flag) continue;
					if (Flag.flag) System.out.printf(">>>>> basePath = %s%n", lnsMstInfo.getBasePath());
					if (Flag.flag) System.out.printf(">>>>> fileName = %s%n", lnsMstInfo.getFileName());
					if (Flag.flag) System.out.printf(">>>>> filePath = %s%n", lnsMstInfo.getFilePath());
					if (Flag.flag) System.out.printf(">>>>> lastModified = %d%n", lnsMstInfo.getLastModfied());
					if (Flag.flag) System.out.printf(">>>>> strJsonInfo = %s%n", lnsMstInfo.getStrJsonInfo());
					if (Flag.flag) System.out.printf(">>>>> reqResType = %s%n", lnsMstInfo.getReqResType());
					if (Flag.flag) System.out.printf(">>>>> infoNode = %s%n", lnsMstInfo.getInfoNode().toPrettyString());
					if (Flag.flag) System.out.printf(">>>>> headBaseInfoNode = %s%n", lnsMstInfo.getHeadBaseInfoNode().toPrettyString());
					if (Flag.flag) System.out.printf(">>>>> headDataInfoNode = %s%n", lnsMstInfo.getHeadDataInfoNode().toPrettyString());
					if (Flag.flag) System.out.printf(">>>>> bodyBaseInfoNode = %s%n", lnsMstInfo.getBodyBaseInfoNode().toPrettyString());
					if (Flag.flag) System.out.printf(">>>>> bodyDataInfoNode = %s%n", lnsMstInfo.getBodyDataInfoNode().toPrettyString());
				}
			}
		}
		
		
		if (Flag.flag) {
			File fileBasePath = new File(basePath);
			while (true) {
				for (final File fileEntry : fileBasePath.listFiles()) {
					if (fileEntry.isDirectory()) {
						// subdirectory
					} else if (fileEntry.isFile()) {
						// file
						LnsMstInfo lnsMstInfo = mapInfo.get(fileEntry.getName());
						if (lnsMstInfo.checkAndUpdate(fileEntry)) {
							if (Flag.flag) System.out.println(">>>>> filename = " + lnsMstInfo.getInfoNode().toPrettyString());
							
							LnsMstInfo lnsMstInfo2 = mapInfo.get("0700100");
							if (Flag.flag) System.out.println(">>>>> 0700100 = " + lnsMstInfo2.getInfoNode().toPrettyString());
							if (Flag.flag) System.out.printf(">>>>> %s file is updated.%n", fileEntry.getName());
						}
					}
				}
				
				Sleep.run(10 * 1000);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) Sleep.run(3 * 1000);
		
		String strCStruct = "";
		String strLength = "";
		if (Flag.flag) {
			// CStruct
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700100");
			strCStruct = new LnsCStruct(lnsMstInfo).get();
			strLength = new LnsStreamLength(lnsMstInfo).getStrLength();
			if (Flag.flag) System.out.println(">>>>> CStruct = " + strCStruct);
			if (Flag.flag) System.out.println(">>>>> StreamLength = " + strLength);
		}
		
		String strStream = "";
		if (Flag.flag) {
			// JsonToStream
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("test.req.json");
			strStream = new LnsJsonToStream(lnsMstInfo, strJsonData01).get();
			if (Flag.flag) System.out.println(">>>>> LnsJsonToStream = " + strStream);
		}
		
		String strJson = "";
		if (Flag.flag) {
			// StreamToJson
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700100");
			strJson = new LnsStreamToJson(lnsMstInfo, strStream).get().toPrettyString();
			if (Flag.flag) System.out.println(">>>>> LnsStreamToJson = " + strJson);
		}
	}
}
