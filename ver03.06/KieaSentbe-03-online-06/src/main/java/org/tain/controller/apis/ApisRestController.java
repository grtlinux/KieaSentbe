package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tain.mapper.LnsJsonNode;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.LnsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/online"})
@Slf4j
public class ApisRestController {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	/*
	 * url: http://localhost:18083/v1.0/online/getCalculation
	 */
	@RequestMapping(value = {"/process"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> process(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		String strBody = null;
		if (Flag.flag) {
			strBody = reqHttpEntity.getBody();
			log.info("ONLINE-1 >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("ONLINE-1 >>>>> Body = {}", strBody);
		}
		
		String reqResType = null;
		String reqJson = null;
		if (Flag.flag) {
			JsonNode jsonNode = new ObjectMapper().readTree(strBody);
			String strReqRes = jsonNode.at("/__head_data").get("reqres").asText();
			String strType = jsonNode.at("/__head_data").get("type").asText();
			
			reqResType = strReqRes + strType;
			log.info("ONLINE-2 >>>>> reqResType = {}", reqResType);
			
			reqJson = jsonNode.toPrettyString();
			log.info("ONLINE-2 >>>>> reqJson = {}", reqJson);
		}
		
		String resJson = null;
		if (Flag.flag) {
			// 2. link
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/link/process");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", reqResType);
			lnsJsonNode.put("reqJson", reqJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
			
			resJson = lnsJsonNode.getValue("resJson");
			log.info("ONLINE-3 >>>>> resJson = {}", resJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(resJson, headers, HttpStatus.OK);
	}
}
