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
import org.tain.mapper.LnsNodeTools;
import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsRecvQueue;
import org.tain.queue.LnsSendQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.LnsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/callback/getVerification"})
@Slf4j
public class CallbackRestController {

	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private LnsRecvQueue lnsRecvQueue;
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	/*
	 * url: http://localhost:17083/v0.6/callback/getVerification
	 */
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> checkUser(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		String reqJson = null;
		if (Flag.flag) {
			reqJson = "{ \"user_id\" : \"1001329\"}";
		}
		
		LnsJsonNode reqJsonNode = null;
		if (Flag.flag) {
			// make LnsJsonNode
			LnsJsonNode reqHeadNode = new LnsJsonNode();
			reqHeadNode.put("length", "0000");
			reqHeadNode.put("reqres", "0700");
			reqHeadNode.put("type", "700");
			reqHeadNode.put("trNo", "000000");
			reqHeadNode.put("reqDate", LnsNodeTools.getDate());
			reqHeadNode.put("reqTime", LnsNodeTools.getTime());
			reqHeadNode.put("resTime", "");
			reqHeadNode.put("resCode", "");
			reqHeadNode.put("resMessage", "");
			//LnsJsonNode reqBodyNode = new LnsJsonNode(reqHttpEntity.getBody());
			LnsJsonNode reqBodyNode = new LnsJsonNode(reqJson);
			
			reqJsonNode = new LnsJsonNode();
			reqJsonNode.put("__head_data", reqHeadNode.get());
			reqJsonNode.put("__body_data", reqBodyNode.get());
			
			if (Flag.flag) log.info(">>>>> reqJsonNode =  {}", reqJsonNode.toPrettyString());
		}
		
		String strReqStream = null;
		if (Flag.flag) {
			// json to stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700700");
			lnsJsonNode.put("json", reqJsonNode.toPrettyString());
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			if (Flag.flag) log.info(">>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
			
			// get stream
			strReqStream = lnsJsonNode.getValue("stream");
			if (Flag.flag) log.info(">>>>> lnsJsonNode.stream = [{}]", strReqStream);
		}
		
		String strResStream = null;
		if (Flag.flag) {
			// send
			LnsStream reqLnsStream = new LnsStream(strReqStream);
			this.lnsSendQueue.set(reqLnsStream);
			
			// connect and processing
			//strResStream = "0106071070000000020201020093033                                                                     HW2009201010        ";
			
			// recv
			LnsStream resLnsStream = (LnsStream) this.lnsRecvQueue.get();
			strResStream = resLnsStream.getData();
		}
		
		LnsJsonNode resLnsJsonNode = null;
		if (Flag.flag) {
			// stream to json
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710700");
			lnsJsonNode.put("stream", strResStream);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			if (Flag.flag) log.info(">>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
			
			// get json
			String json = lnsJsonNode.getValue("json");
			if (Flag.flag) log.info(">>>>> lnsJsonNode.json = {}", json);
			
			resLnsJsonNode = new LnsJsonNode(json);
		}
		
		String resJson = null;
		if (Flag.flag) {
			// get json to return
			JsonNode resJsonNode = resLnsJsonNode.get().get("__body_data");
			resJson = resJsonNode.toPrettyString();
			if (Flag.flag) log.info(">>>>> resJson = {}", resJson);
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		return new ResponseEntity<>(resJson, headers, HttpStatus.OK);
	}
}
