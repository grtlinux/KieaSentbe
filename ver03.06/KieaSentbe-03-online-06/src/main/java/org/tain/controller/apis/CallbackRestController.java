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

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/callback"})
@Slf4j
public class CallbackRestController {

	@SuppressWarnings("unused")
	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	/*
	 * url: http://localhost:17083/v0.6/callback/getVerification
	 */
	@RequestMapping(value = {"/getVerification"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkUser(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("name", "apis/getVerification");
			lnsJsonNode.put("httpUrl", "http://localhost:17083/v0.6/callback/getVerification");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700700");
			lnsJsonNode.put("reqJson", reqHttpEntity.getBody());
			if (Flag.flag) log.info(">>>>> BEFORE: lnsJsonNode = {}", lnsJsonNode.toPrettyString());
			
			//lnsJsonNode = this.lnsHttpClient2.post(lnsJsonNode);
			
			lnsJsonNode.put("reqResType", "0710700");
			lnsJsonNode.put("resJson", "{ \"verification_code\" : \"HW20201015\" }");
			if (Flag.flag) log.info(">>>>> AFTER: lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
		
		String resJson = null;
		if (Flag.flag) {
			resJson = lnsJsonNode.getValue("resJson");
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		return new ResponseEntity<>(resJson, headers, HttpStatus.OK);
	}
}
