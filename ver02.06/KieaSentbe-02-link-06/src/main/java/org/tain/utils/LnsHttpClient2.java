package org.tain.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonNode;
import org.tain.utils.enums.RestTemplateType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LnsHttpClient2 {

	///////////////////////////////////////////////////////////////////////////
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode) throws Exception {
		return this.post(lnsJsonNode, false);
	}
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode, boolean flag) throws Exception {
		if (Flag.flag) log.info("========================== START =========================");
		if (Flag.flag) log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String reqJson = null;
		if (Flag.flag) {
			log.info(">>>>> flag: {}, lnsJsonNode: {}", flag, lnsJsonNode.toPrettyString());
			reqJson = lnsJsonNode.getValue("reqJson");
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJsonNode.getValue("httpUrl");
			HttpMethod httpMethod = HttpMethod.POST;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(reqJson, reqHeaders);
			log.info(">>>>> REQ.reqHttpEntity  = {}", reqHttpEntity);
			
			ResponseEntity<String> response = null;
			try {
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				log.info(">>>>> RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.info(">>>>> RES.getStatusCode()      = {}", response.getStatusCode());
				log.info(">>>>> RES.getBody()            = {}", response.getBody());
				
				if (Flag.flag) {
					String resJson = response.getBody();
					lnsJsonNode.put("resJson", resJson);
					lnsJsonNode.put("reqResType", "0710700");
				}
				log.info(">>>>> RES.lnsJsonNode          = {}", lnsJsonNode.toPrettyString());
			} catch (Exception e) {
				// TODO: 
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJsonNode.put("resCode", "999");
				lnsJsonNode.put("resMessage", "FAIL");
				lnsJsonNode.put("errMessage", message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Flag.flag) log.info("========================== END ===========================");
		
		return lnsJsonNode;
	}
}
