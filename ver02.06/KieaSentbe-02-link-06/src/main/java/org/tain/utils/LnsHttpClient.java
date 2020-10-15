package org.tain.utils;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsNodeTools;
import org.tain.object.lns.LnsJson;
import org.tain.utils.enums.RestTemplateType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LnsHttpClient {

	public LnsJson get(LnsJson lnsJson) throws Exception {
		return get(lnsJson, false);
	}
	
	public LnsJson get(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("================== START: {} ===================", lnsJson.getName());
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
			log.info("================== FINISH: {} ===================", lnsJson.getName());
		}
		
		return lnsJson;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode) throws Exception {
		return post(lnsJsonNode, false);
	}
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode, boolean flag) throws Exception {
		if (Flag.flag) log.info("========================== START =========================");
		if (Flag.flag) log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info(">>>>> flag: {}", flag);
			log.info(">>>>> lnsJsonNode: {}", lnsJsonNode.toPrettyString());
		}
		
		String strReqJson = null;
		LnsJsonNode reqHeadNode = null;
		LnsJsonNode reqBodyNode = null;
		if (Flag.flag) {
			LnsJsonNode reqNode = new LnsJsonNode(lnsJsonNode.getValue("reqJson"));
			if (Flag.flag) log.info(">>>>> reqNode = {}", reqNode.toPrettyString());
			
			reqHeadNode = new LnsJsonNode(reqNode.get().path("__head_data"));
			reqBodyNode = new LnsJsonNode(reqNode.get().path("__body_data"));
			
			strReqJson = reqBodyNode.toPrettyString();
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJsonNode.getValue("httpUrl");
			HttpMethod httpMethod = HttpMethod.POST;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(strReqJson, reqHeaders);
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
				String strResJson = response.getBody();
				
				if (Flag.flag) {
					LnsJsonNode resHeadNode = new LnsJsonNode(reqHeadNode.get());
					LnsJsonNode resBodyNode = new LnsJsonNode(strResJson);
					
					resHeadNode.put("reqres", "0710");
					resHeadNode.put("resTime", LnsNodeTools.getTime());
					resHeadNode.put("resCode", "000");
					resHeadNode.put("resMessage", "SUCCESS");
					
					// add reshead + resbody
				}
				
				
				
				lnsJsonNode = new LnsJsonNode(strReqJson);
				log.info(">>>>> RES.lnsJsonNode          = {}", lnsJsonNode.toPrettyString());
			} catch (Exception e) {
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
	
	///////////////////////////////////////////////////////////////////////////
	
	@Deprecated
	public LnsJson post(LnsJson lnsJson) throws Exception {
		return post(lnsJson, false);
	}
	
	@Deprecated
	public LnsJson post(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("================== START: {} ===================", lnsJson.getName());
			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.POST;
			
			//String json = JsonPrint.getInstance().toPrettyJson(lnsJson);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode headNode = null;
			JsonNode bodyNode = null;
			String json = null;
			if (Flag.flag) {
				// del header
				JsonNode node = objectMapper.readTree(lnsJson.getReqJsonData());
				headNode = node.at("/__head_data");
				bodyNode = node.at("/__body_data");
				json = bodyNode.toPrettyString();
			}
			log.info(">>>>> REQ.lnsJson        = {}", json);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
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
				if (Flag.flag) {
					// add header
					ObjectNode node = (ObjectNode) headNode;
					node.put("reqres", "0710");
					node.put("resTime", LnsNodeTools.getTime());
					node.put("resCode", "000");
					node.put("resMessage", "SUCCESS");
					
					JsonNode jsonNode = (JsonNode) objectMapper.createObjectNode();
					((ObjectNode) jsonNode).set("__head_data", node);
					((ObjectNode) jsonNode).set("__body_data", objectMapper.readTree(response.getBody()));
					json = jsonNode.toPrettyString();
				}
				lnsJson.setResJsonData(json);
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
			log.info("================== FINISH: {} ===================", lnsJson.getName());
		}
		
		return lnsJson;
	}
}
