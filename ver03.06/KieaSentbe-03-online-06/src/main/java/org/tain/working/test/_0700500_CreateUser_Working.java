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
public class _0700500_CreateUser_Working {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public void test01() throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		String strReqStream = "0374070050099999920201016125525125525                                                               "
				+ "1SSFCTS    EFTS      PRIVACY   CAUTIONS  CLAIMS                        KR      1012340000Jiwon                                   Tak                 105012345670000      Jiwon Tak           19701225KR 701225-1230000      1email0000@sentbe.com                              PH 333";
		
		String strResStream = "0328071050099999920201016125525125525                                                               "
				+ "                    1001329   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyODc5MTcsImp0aSI6ImJ0bzZhcjhzajVvZzNqbWdrcjJnIiwidXNlcl9pZCI6MTAwMTMyOX0.cd03WgJEX-AABa5t39jaD-zXqDVl__f8BY6bOWinLIY                                0 ";
		
		String strReqJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				"    \"length\" : \"0374\",\n" + 
				"    \"reqres\" : \"0700\",\n" + 
				"    \"type\" : \"500\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"agreements\" : {\n" + 
				"      \"version\" : \"1\",\n" + 
				"      \"list\" : [ \"SSFCTS\", \"EFTS\", \"PRIVACY\", \"CAUTIONS\", \"CLAIMS\" ]\n" + 
				"    },\n" + 
				"    \"phone\" : {\n" + 
				"      \"iso\" : \"KR\",\n" + 
				"      \"number\" : 1012340000\n" + 
				"    },\n" + 
				"    \"user_name\" : {\n" + 
				"      \"first\" : \"Jiwon\",\n" + 
				"      \"last\" : \"Tak\"\n" + 
				"    },\n" + 
				"    \"gender\" : 1,\n" + 
				"    \"account_number\" : \"05012345670000\",\n" + 
				"    \"account_holder_name\" : \"Jiwon Tak\",\n" + 
				"    \"birth_date\" : \"19701225\",\n" + 
				"    \"nationality_iso\" : \"KR\",\n" + 
				"    \"id_number\" : \"701225-1230000\",\n" + 
				"    \"id_type\" : 1,\n" + 
				"    \"email\" : \"email0000@sentbe.com\",\n" + 
				"    \"often_send_country_iso\" : \"PH\",\n" + 
				"    \"occupation\" : 3,\n" + 
				"    \"funds_source\" : 3,\n" + 
				"    \"transfer_purpose\" : 3\n" + 
				"  }\n" + 
				"}";
		
		String strResJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				"    \"length\" : \"0328\",\n" + 
				"    \"reqres\" : \"0710\",\n" + 
				"    \"type\" : \"500\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"user_id\" : \"1001329\",\n" + 
				"    \"webview_id\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyODc5MTcsImp0aSI6ImJ0bzZhcjhzajVvZzNqbWdrcjJnIiwidXNlcl9pZCI6MTAwMTMyOX0.cd03WgJEX-AABa5t39jaD-zXqDVl__f8BY6bOWinLIY\",\n" + 
				"    \"verification_id\" : \"0\"\n" + 
				"  }\n" + 
				"}";
		
		if (Flag.flag) {
			// 1. reqCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700500");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-1 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 2. resCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710500");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-2 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 3. Stream to Json of strReqStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700500");
			lnsJsonNode.put("stream", strReqStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 4. Stream to Json of strResStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710500");
			lnsJsonNode.put("stream", strResStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-4 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 5. Json to Stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710500");
			lnsJsonNode.put("json", strResJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-5 >>>>> lnsJsonNode.j2s {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("stream"));
		}
		
		if (Flag.flag) {
			// 6. link
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/link/process");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700500");
			lnsJsonNode.put("reqJson", strReqJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-6 >>>>> lnsJsonNode.link {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("resJson"));
		}
	}
}
