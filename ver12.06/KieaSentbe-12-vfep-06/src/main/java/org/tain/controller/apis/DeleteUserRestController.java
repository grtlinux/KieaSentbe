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
import org.tain.object.lns.LnsJson;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/apis/deleteUser"})
@Slf4j
public class DeleteUserRestController {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	@RequestMapping(value = {"/mapper/req/json2str"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> mapperReqJson2Str(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode node = null;
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
			node = JsonPrint.getInstance().getObjectMapper().readTree(reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("DeleteUser mapperReqJson2Str");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/deleteUser/req/j2s");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType(node.at("/__head_data").get("type").asText());  // TODO
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/sbs01"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> deleteUser(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode node = null;
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
			node = JsonPrint.getInstance().getObjectMapper().readTree(reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("DeleteUser sbs01");
			lnsJson.setHttpUrl("http://localhost:17089/v0.5/sbs01/deleteUser");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType(node.at("/__head_data").get("type").asText());  // TODO
			
			//JsonNode jsonNode = new ObjectMapper().readTree(reqHttpEntity.getBody());
			//lnsJson.setReqStrData("" + jsonNode.get("reqStrData").asText());  // len(4) + method(4) + division(3)
			lnsJson.setReqStrData("" + reqHttpEntity.getBody());  // len(4) + method(4) + division(3)
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/mapper/res/str2json"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> mapperResStr2Json(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode node = null;
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
			node = JsonPrint.getInstance().getObjectMapper().readTree(reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("DeleteUser mapperResStr2Json");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/deleteUser/res/s2j");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType(node.at("/__head_data").get("type").asText());  // TODO
			
			//JsonNode jsonNode = new ObjectMapper().readTree(reqHttpEntity.getBody());
			//lnsJson.setResStrData("" + jsonNode.get("resStrData").asText());  // len(4) + method(4) + division(3)
			lnsJson.setResStrData("" + reqHttpEntity.getBody());  // len(4) + method(4) + division(3)
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/mapper/req/cstruct"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> mapperReqCStruct(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode node = null;
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
			node = JsonPrint.getInstance().getObjectMapper().readTree(reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("DeleteUser mapperReCStruct");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/deleteUser/req/cstruct");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType(node.get("type").asText());
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/mapper/res/cstruct"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> mapperResCStruct(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode node = null;
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
			node = JsonPrint.getInstance().getObjectMapper().readTree(reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("DeleteUser mapperResCStruct");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/deleteUser/res/cstruct");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType(node.get("type").asText());
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
}
