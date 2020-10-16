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
import org.tain.repository.apis.ApisRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/apis/getCalculation"})
@Slf4j
public class GetCalculationRestController {

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
			Apis apis = this.apisRepository.findApidByMapping("apis/getCalculation");
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
}
