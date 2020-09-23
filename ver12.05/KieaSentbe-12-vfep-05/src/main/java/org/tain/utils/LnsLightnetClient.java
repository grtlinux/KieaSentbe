package org.tain.utils;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.data.LnsData;
import org.tain.object.lns.LnsJson;
import org.tain.utils.enums.RestTemplateType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsLightnetClient {

	public static LnsJson get(LnsJson lnsJson) throws Exception {
		return get(lnsJson, false);
	}
	
	public static LnsJson get(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("================== Lightnet START: {} ===================", lnsJson.getName());
			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.GET;
			
			String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(lnsJson);
			log.info(">>>>> REQ.lnsJson        = {}", json);
			
			Map<String,String> reqMap = new ObjectMapper().readValue(lnsJson.getReqJsonData(), new TypeReference<Map<String,String>>(){});
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.setAll(reqMap);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(httpUrl)
					.queryParams(map)
					.build(true);
			httpUrl = builder.toString();
			log.info(">>>>> REQ.httpUrl(method) = {} ({})", httpUrl, httpMethod);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			if (flagAccessToken) reqHeaders.set("Authorization", "Bearer " + LnsData.getInstance().getAccessToken());
			log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(reqHeaders);
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
				json = response.getBody();
				lnsJson = new ObjectMapper().readValue(json, LnsJson.class);
				log.info(">>>>> RES.lnsJson              = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJson.setCode("99999");
				lnsJson.setStatus("FAIL");
				lnsJson.setMsgJson(message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Flag.flag) {
			log.info("================== Lightnet FINISH: {} ===================", lnsJson.getName());
		}
		
		return lnsJson;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public static LnsJson post(LnsJson lnsJson) throws Exception {
		return post(lnsJson, false);
	}
	
	public static LnsJson post(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("================== Lightnet START: {} ===================", lnsJson.getName());
			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.POST;
			
			String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(lnsJson.getReqJsonData());
			//JsonNode jsonNode = new ObjectMapper().readTree(lnsReqWeb.getReqJson());
			//((ObjectNode) jsonNode.at("/source")).put("transactionId", TransactionId.get());
			//reqJson = jsonNode.toPrettyString();
			log.info(">>>>> REQ.lnsJson        = {}", json);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			if (flagAccessToken) reqHeaders.set("Authorization", "Bearer " + LnsData.getInstance().getAccessToken());
			log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(json, reqHeaders);
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
				json = response.getBody();
				lnsJson = new ObjectMapper().readValue(json, LnsJson.class);
				log.info(">>>>> RES.lnsJson              = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJson.setCode("99999");
				lnsJson.setStatus("FAIL");
				lnsJson.setMsgJson(message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Flag.flag) {
			log.info("================== Lightnet FINISH: {} ===================", lnsJson.getName());
		}
		
		return lnsJson;
	}
}
