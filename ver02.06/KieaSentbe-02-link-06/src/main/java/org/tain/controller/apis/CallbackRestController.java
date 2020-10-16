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
import org.tain.properties.ProjEnvUrlProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.LnsHttpClient;
import org.tain.utils.LnsSentbeClient;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/callback"})
@Slf4j
@SuppressWarnings("unused")
public class CallbackRestController {

	@Autowired
	private ProjEnvUrlProperties projEnvUrlProperties;
	
	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	@Autowired
	private LnsSentbeClient lnsSentbeClient;
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = {"/getVerification"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> process(HttpEntity<String> reqHttpEntity) throws Exception {
		if (Flag.flag) log.info("\n================== START: link ===================\n");
		if (Flag.flag) log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("LINK >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("LINK >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode lnsJsonNode = null;
		String reqResType = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode(reqHttpEntity.getBody());
			reqResType = lnsJsonNode.getValue("reqResType");
			if (Flag.flag) log.info(">>>>> [{}] REQ.lnsJsonNode = {}", reqResType, lnsJsonNode.toPrettyString());
		}
		/*
		switch (reqResType) {
		case "0700100":  // checkUser
			lnsJsonNode = process_0700100_checkUser(lnsJsonNode);
			break;
		case "0700200":  // getWebviewId
			lnsJsonNode = process_0700200_getCalculation(lnsJsonNode);
			break;
		case "0700300":  // deleteUser
			lnsJsonNode = process_0700300_deleteUser(lnsJsonNode);
			break;
		case "0700400":  // getWebviewId
			lnsJsonNode = process_0700400_getWebviewId(lnsJsonNode);
			break;
		case "0700500":  // createUser
			lnsJsonNode = process_0700500_createUser(lnsJsonNode);
			break;
		case "0700600":  // getResult
			lnsJsonNode = process_0700600_getResult(lnsJsonNode);
			break;
		case "0700700":  // getVerification
			lnsJsonNode = process_0700700_getVerification(lnsJsonNode);
			break;
		case "0700800":  // migrationUser
			lnsJsonNode = process_0700800_migrationUser(lnsJsonNode);
			break;
		default:
			throw new RuntimeException("WRONG REQRESTYPE....");
		}
		*/
		if (Flag.flag) Sleep.run(1 * 1000);
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		if (Flag.flag) log.info("\n================== END: link ===================\n");
		
		return new ResponseEntity<>(lnsJsonNode.toPrettyString(), headers, HttpStatus.OK);
	}
}
