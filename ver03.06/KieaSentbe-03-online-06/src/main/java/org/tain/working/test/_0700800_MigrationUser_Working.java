package org.tain.working.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonNode;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.LnsHttpClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class _0700800_MigrationUser_Working {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public void test01() throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		String strReqStream = "0292070080099999920201016125525125525                                                               "
				+ "                    1001099   KR      1012345678Heung-min                               Son                 105012345333323      Heung-min Son       19701225KR                               PH 333";
		
		String strResStream = "0306071080099999920201016125525125525                                                               "
				+ "1001099   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEwOTQzMTIsImp0aSI6ImJ0bW4 yYTZiczRnaGhpMGprMmEwIiwidXNlcl9pZCI6MTAwMTA5OX0.uOq8Ui_qPjee_pxfP- zEqU5kf8Nvf3kJNI8f2WC8uPA                              ";
		
		String strReqJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				"    \"length\" : \"0292\",\n" + 
				"    \"reqres\" : \"0700\",\n" + 
				"    \"type\" : \"800\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"user_id\" : \"1001099\",\n" + 
				"    \"phone\" : {\n" + 
				"      \"iso\" : \"KR\",\n" + 
				"      \"number\" : 1012345678\n" + 
				"    },\n" + 
				"    \"user_name\" : {\n" + 
				"      \"first\" : \"Heung-min\",\n" + 
				"      \"last\" : \"Son\"\n" + 
				"    },\n" + 
				"    \"gender\" : 1,\n" + 
				"    \"account_number\" : \"05012345333323\",\n" + 
				"    \"account_holder_name\" : \"Heung-min Son\",\n" + 
				"    \"birth_date\" : \"19701225\",\n" + 
				"    \"nationality_iso\" : \"KR\",\n" + 
				"    \"often_send_country_iso\" : \"PH\",\n" + 
				"    \"occupation\" : 3,\n" + 
				"    \"funds_source\" : 3,\n" + 
				"    \"transfer_purpose\" : 3\n" + 
				"  }\n" + 
				"}";
		
		String strResJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				"    \"length\" : \"0306\",\n" + 
				"    \"reqres\" : \"0710\",\n" + 
				"    \"type\" : \"800\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"user_id\" : \"1001099\",\n" + 
				"    \"webview_id\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEwOTQzMTIsImp0aSI6ImJ0bW4 yYTZiczRnaGhpMGprMmEwIiwidXNlcl9pZCI6MTAwMTA5OX0.uOq8Ui_qPjee_pxfP- zEqU5kf8Nvf3kJNI8f2WC8uPA\"\n" + 
				"  }\n" + 
				"}";
		
		if (Flag.flag) {
			// 1. reqCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700800");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-1 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 2. resCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710800");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-2 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 3. Stream to Json of strReqStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700800");
			lnsJsonNode.put("stream", strReqStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 4. Stream to Json of strResStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710800");
			lnsJsonNode.put("stream", strResStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-4 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 5. Json to Stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710800");
			lnsJsonNode.put("json", strResJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-5 >>>>> lnsJsonNode.j2s {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("stream"));
		}
		
		if (Flag.flag) {
			// 6. link
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/link/process");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700800");
			lnsJsonNode.put("reqJson", strReqJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-6 >>>>> lnsJsonNode.link {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("resJson"));
		}
	}
}
