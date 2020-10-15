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

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/online/"})
@Slf4j
public class ApisRestController {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	/*
	 * url: http://localhost:18083/v1.0/online/getCalculation
	 */
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCalculation(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("SIT >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SIT >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		String resJson = null;
		if (Flag.flag) {
			LnsJson lnsJson = new LnsJson();
			lnsJson.setName("GetCalculation sbs01");
			lnsJson.setHttpUrl("http://localhost:17082/v0.5/link/getCalculation");
			lnsJson.setHttpMethod("POST");
			lnsJson.setReqJsonData(reqHttpEntity.getBody());
			log.info("ONLINE:GetCalculation >>>>> REQ.lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			
			lnsJson = this.lnsHttpClient.post(lnsJson);
			log.info("ONLINE:GetCalculation >>>>> RES.lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			
			resJson = lnsJson.getResJsonData();
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(resJson, headers, HttpStatus.OK);
	}
}
