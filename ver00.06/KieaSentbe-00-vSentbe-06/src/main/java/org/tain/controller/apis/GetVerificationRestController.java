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
import org.tain.domain.apis.Apis;
import org.tain.object.lns.LnsJson;
import org.tain.repository.apis.ApisRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/apis/getVerification"})
@Slf4j
public class GetVerificationRestController {

	@Autowired
	private ApisRepository apisRepository;
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> main(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			/*
			UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
			String requestedValue = builder.buildAndExpand().getPath();  // I want this.
			System.out.println(">>>>> requestedValue = " + requestedValue);
			// I want to do something like this with requested value.
			String result="fail"; 
			if (requestedValue.equals("center"))
				result = "center";
			else if (requestedValue.equals("left"))
				result = "left";
			System.out.println(">>>>> result = " + result);
			*/
		}
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		String jsonRes = null;
		if (Flag.flag) {
			Apis apis = this.apisRepository.findApidByMapping("apis/getVerification");
			if (Flag.flag) System.out.println(">>>>> apis = " + JsonPrint.getInstance().toPrettyJson(apis));
			
			String strRes = apis.getResJson();
			JsonNode jsonNodeRes = new ObjectMapper().readTree(strRes);
			jsonRes = jsonNodeRes.toPrettyString();
			if (Flag.flag) System.out.println(">>>>> jsonRes = " + jsonRes);
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		return new ResponseEntity<>(jsonRes, headers, HttpStatus.OK);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	@RequestMapping(value = {"/mapper/req/json2str"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> mapperReqJson2Str(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("GetVerification mapperReqJson2Str");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getVerification/req/j2s");
			lnsJson.setHttpMethod("POST");
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = {"/sbs01"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getVerification(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("GetVerification sbs01");
			lnsJson.setHttpUrl("http://localhost:17089/v0.5/sbs01/getVerification");
			lnsJson.setHttpMethod("POST");
			
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
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("GetVerification mapperResStr2Json");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getVerification/res/s2j");
			lnsJson.setHttpMethod("POST");
			
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
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("GetVerification mapperResStr2Json");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getVerification/req/cstruct");
			lnsJson.setHttpMethod("POST");
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
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new LnsJson();
			lnsJson.setName("GetVerification mapperResStr2Json");
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getVerification/res/cstruct");
			lnsJson.setHttpMethod("POST");
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
}
