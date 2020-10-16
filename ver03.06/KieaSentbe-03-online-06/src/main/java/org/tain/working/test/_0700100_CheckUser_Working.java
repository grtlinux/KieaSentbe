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
public class _0700100_CheckUser_Working {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public void test01() throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		String strReqStream = "0116070010099999920201016125525125525                                                               "
				+ "701225-1234567      ";
		
		String strResStream = "0181071010099999920201016125525125525                                                               "
				+ "7012251234567       true falsefalse                    1001088   05012345678900      ";
		
		String strReqJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				//"    \"length\" : \"0116\",\n" + 
				"    \"length\" : \"0000\",\n" + 
				"    \"reqres\" : \"0700\",\n" + 
				"    \"type\" : \"100\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"id_number\" : \"701225-1234567\"\n" + 
				"  }\n" + 
				"}";
		
		String strResJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				//"    \"length\" : \"0181\",\n" + 
				"    \"length\" : \"0000\",\n" + 
				"    \"reqres\" : \"0710\",\n" + 
				"    \"type\" : \"100\",\n" + 
				"    \"trNo\" : \"999999\",\n" + 
				"    \"reqDate\" : \"20201016\",\n" + 
				"    \"reqTime\" : \"125525\",\n" + 
				"    \"resTime\" : \"125525\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"id_number\" : \"7012251234567\",\n" + 
				"    \"sign_status\" : true,\n" + 
				"    \"sentbe_exist_status\" : false,\n" + 
				"    \"remittable_status\" : false,\n" + 
				"    \"user_id\" : \"1001088\",\n" + 
				"    \"account_number\" : \"05012345678900\"\n" + 
				"  }\n" + 
				"}";
		
		if (Flag.flag) {
			// 1. reqCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700100");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-1 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 2. resCStruct
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/cstruct");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710100");
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-2 >>>>> lnsJsonNode.cstruct {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("cstruct"));
		}
		
		if (Flag.flag) {
			// 3. Stream to Json of strReqStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700100");
			lnsJsonNode.put("stream", strReqStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 4. Stream to Json of strResStream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710100");
			lnsJsonNode.put("stream", strResStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-4 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		if (Flag.flag) {
			// 5. Json to Stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710100");
			lnsJsonNode.put("json", strResJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-5 >>>>> lnsJsonNode.j2s {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("stream"));
		}
		
		if (Flag.flag) {
			// 6. link
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/link/process");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700100");
			lnsJsonNode.put("reqJson", strReqJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-6 >>>>> lnsJsonNode.link {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("resJson"));
		}
	}
}
