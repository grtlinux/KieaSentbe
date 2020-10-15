package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsNodeTools;
import org.tain.properties.ProjEnvUrlProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.LnsHttpClient;
import org.tain.utils.LnsSentbeClient;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/link/process"})
@Slf4j
@SuppressWarnings("unused")
public class ApisRestController {

	@Autowired
	private ProjEnvUrlProperties projEnvUrlProperties;
	
	@Autowired
	private LnsSentbeClient lnsSentbeClient;
	
	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> reqStrToJson(HttpEntity<String> reqHttpEntity) throws Exception {
		if (Flag.flag) log.info("\n================== Link START ===================\n");
		if (Flag.flag) log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("LINK >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("LINK >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode reqHeadNode = null;
		LnsJsonNode reqBodyNode = null;
		if (Flag.flag) {
			LnsJsonNode lnsJsonNode = new LnsJsonNode(reqHttpEntity.getBody());
			LnsJsonNode reqNode = new LnsJsonNode(lnsJsonNode.getValue("reqJson"));
			if (Flag.flag) log.info(">>>>> REQ.reqNode = {}", reqNode.toPrettyString());
			reqHeadNode = new LnsJsonNode(reqNode.get().path("__head_data"));
			reqBodyNode = new LnsJsonNode(reqNode.get().path("__body_data"));
			String reqResType = lnsJsonNode.getValue("reqResType");
			if (Flag.flag) log.info(">>>>> REQ.lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
		
		String strResJson = null;
		if (Flag.flag) {
			/*
			strResJson = "{\n" + 
					"  \"__head_data\" : {\n" + 
					"    \"length\" : \"0162\",\n" + 
					"    \"reqres\" : \"0710\",\n" + 
					"    \"type\" : \"200\",\n" + 
					"    \"trNo\" : \"999999\",\n" + 
					"    \"reqDate\" : \"20201015\",\n" + 
					"    \"reqTime\" : \"080241\",\n" + 
					"    \"resTime\" : \"080241\",\n" + 
					"    \"resCode\" : \"\",\n" + 
					"    \"resMessage\" : \"\",\n" + 
					"    \"reserved\" : \"\"\n" + 
					"  },\n" + 
					"  \"__body_data\" : {\n" + 
					"    \"exchange_rate_id\" : \"20190605175000\",\n" + 
					"    \"from_amount\" : 1000000,\n" + 
					"    \"from_currency\" : \"KRW\",\n" + 
					"    \"to_amount\" : 43474,\n" + 
					"    \"to_currency\" : \"PHP\",\n" + 
					"    \"transfer_fee\" : 0,\n" + 
					"    \"base_rate\" : 23.00184\n" + 
					"  }\n" + 
					"}";
					*/
			strResJson = "{\n" + 
					"  \"__head_data\" : {\n" + 
					"    \"length\" : \"0306\",\n" + 
					"    \"reqres\" : \"0710\",\n" + 
					"    \"type\" : \"400\",\n" + 
					"    \"trNo\" : \"999999\",\n" + 
					"    \"reqDate\" : \"20201015\",\n" + 
					"    \"reqTime\" : \"100116\",\n" + 
					"    \"resTime\" : \"100116\",\n" + 
					"    \"resCode\" : \"\",\n" + 
					"    \"resMessage\" : \"\",\n" + 
					"    \"reserved\" : \"\"\n" + 
					"  },\n" + 
					"  \"__body_data\" : {\n" + 
					"    \"user_id\" : \"82150\",\n" + 
					"    \"webview_id\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiwidXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI\"\n" + 
					"  }\n" + 
					"}";
			
			Sleep.run(1 * 1000);
		}
		
		LnsJsonNode resHeadNode = null;
		LnsJsonNode resBodyNode = null;
		if (Flag.flag) {
			LnsJsonNode lnsJsonNode = new LnsJsonNode(strResJson);
			resHeadNode = new LnsJsonNode(lnsJsonNode.get().path("__head_data"));
			resBodyNode = new LnsJsonNode(lnsJsonNode.get().path("__body_data"));
		}
		
		String reqResType = null;
		if (Flag.flag) {
			resHeadNode.put("trNo", reqHeadNode.getValue("trNo"));
			resHeadNode.put("resTime", LnsNodeTools.getTime());
			resHeadNode.put("resCode", "000");
			resHeadNode.put("resMessage", "SUCCESS");
			reqResType = resHeadNode.getValue("reqres") + resHeadNode.getValue("type");
		}
		
		LnsJsonNode resNode = new LnsJsonNode();
		if (Flag.flag) {
			resNode.put("reqResType", reqResType);
			LnsJsonNode resJsonNode = new LnsJsonNode();
			resJsonNode.put("__head_data", resHeadNode.get());
			resJsonNode.put("__body_data", resBodyNode.get());
			resNode.put("resJson", resJsonNode.toPrettyString());
			if (Flag.flag) log.info(">>>>> RES.resNode = {}", resNode.toPrettyString());
		}
		
		/*
		LnsJson lnsJson = null;
		if (Flag.flag) {
			String sentbe = this.projEnvUrlProperties.getSentbe();
			if (sentbe.contains("localhost")) {
				lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
				lnsJson.setHttpUrl(sentbe + "/apis/getCalculation");
				lnsJson.setHttpMethod("POST");
				lnsJson = this.lnsHttpClient.post(lnsJson);
			} else {
				lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
				lnsJson.setHttpUrl(sentbe + "/hanwha/getCalculation");
				lnsJson.setHttpMethod("POST");
				lnsJson = this.lnsSentbeClient.post(lnsJson);
			}
			log.info(">>>>> RES.CheckUser.lnsJson  = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		*/
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		if (Flag.flag) log.info("\n================== Link FINISH ===================\n");
		
		return new ResponseEntity<>(resNode.toPrettyString(), headers, HttpStatus.OK);
	}
}
