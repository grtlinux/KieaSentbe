package org.tain.utils;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.tain.data.LnsError;
import org.tain.mapper.LnsJsonNode;
import org.tain.object.lns.LnsJson;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.task.ErrorReaderJob;
import org.tain.utils.enums.RestTemplateType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mervick.aes_everywhere.Aes256;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LnsSentbeClient {

	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	@Autowired
	private ErrorReaderJob errorReaderJob;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Deprecated
	public LnsJson get(LnsJson lnsJson) throws Exception {
		return get(lnsJson, false);
	}
	
	@Deprecated
	public LnsJson get(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
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
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode) throws Exception {
		return post(lnsJsonNode, false);
	}
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode, boolean flag) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		JsonNode jsonNode = null;
		JsonNode headNode = null;
		JsonNode bodyNode = null;
		String jsonHeadNode = null;
		String jsonBodyNode = null;
		if (Flag.flag) {
			log.trace("\n\n\n================== Sentbe START: {} ===================", lnsJsonNode.getText("name"));
			log.trace(">>>>> REQ.httpUrl(method): {} ({})", lnsJsonNode.getText("httpUrl"), lnsJsonNode.getText("httpMethod"));
			
			jsonNode = new ObjectMapper().readTree(lnsJsonNode.getText("reqJson"));
			headNode = jsonNode.at("/__head_data");
			bodyNode = jsonNode.at("/__body_data");
			
			jsonHeadNode = headNode.toPrettyString();
			log.trace("KANG-20200721 >>>>> jsonHeadNode = {}", jsonHeadNode);
			
			jsonBodyNode = bodyNode.toPrettyString();
			log.trace("KANG-20200721 >>>>> jsonBodyNode = {}", jsonBodyNode);
		}
		
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-1");
			
			log.trace(">>>>> STEP-1: reqJsonData: {}", jsonBodyNode);
			
			String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();
			String encryptData = Aes256.encrypt(jsonBodyNode, pass);
			log.trace(">>>>> STEP-1: pass(secretKey): {}", pass);
			log.trace(">>>>> STEP-1: encryptData:     {}", encryptData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encryptData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			log.trace(">>>>> STEP-1 jsonPrettyBody:   {}", jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			log.trace(">>>>> STEP-1 jsonBody:         {}", jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-2");
			
			//String url = lnsJsonNode.getValue("httpUrl").substring(8);
			String url = "/" + StringTools.getHttpPath(lnsJsonNode.getText("httpUrl"));
			
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.projEnvParamProperties.getSentbeSecretKeyForHmac();        // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());                       // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);         // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			log.trace(">>>>> STEP-2 client-key       [" + this.projEnvParamProperties.getSentbeClientKey() + "]");
			log.trace(">>>>> STEP-2 secret-key       [" + key + "]");
			log.trace(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			log.trace(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			log.trace(">>>>> STEP-2 url              [" + url + "]");
			log.trace(">>>>> STEP-2 body             [" + body + "]");
			log.trace(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			log.trace(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			log.trace(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			log.trace(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-3");
			
			String httpUrl = lnsJsonNode.getText("httpUrl");
			//String httpUrl = "https://hanwha.dev1.sentbe.com:10443/hanwha/checkUser";
			HttpMethod httpMethod = HttpMethod.POST;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.projEnvParamProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			log.trace(">>>>> STEP-3 reqHeaders: {}", JsonPrint.getInstance().toPrettyJson(reqHeaders));
			log.trace(">>>>> STEP-3 body:       {}", body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			log.trace(">>>>> STEP-3 reqHttpEntity: {}", reqHttpEntity);
			
			//String strResJson = "{}";
			bodyNode = new ObjectMapper().readTree("{}");
			ResponseEntity<String> response = null;
			try {
				if (Flag.flag) {
					// res head
					((ObjectNode)headNode).put("reqres", "0710");
					((ObjectNode)headNode).put("resTime", StringTools.getHHMMSS());
					
					// change reqResType
					String reqResType = lnsJsonNode.getText("reqResType");
					reqResType = "0710" + reqResType.substring(4);
					lnsJsonNode.put("reqResType", reqResType);
				}
				
				// https connection
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				log.trace(">>>>> STEP-3 RES.httpUrl              = {}", httpUrl);
				log.trace(">>>>> STEP-3 RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.trace(">>>>> STEP-3 RES.getStatusCode()      = {}", response.getStatusCode());
				log.trace(">>>>> STEP-3 RES.getBody()            = {}", response.getBody());
				
				if (response.getStatusCodeValue() == 200) {
					JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
					log.trace(">>>>> STEP-3 RES.response.getBody(): {}", jsonResponseBody.toPrettyString());
					
					/*
					if (jsonResponseBody.at("/code").asInt() == 200 && !"".equals(jsonResponseBody.at("/data").asText())) {
						String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();  // secretKey for data
						String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
						
						JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
						lnsJson.setResJsonData(jsonResponseData.toPrettyString());
						log.trace(">>>>> STEP-3 RES.getResJsonData: " + lnsJson.getResJsonData());
					}
					*/
					
					if (jsonResponseBody.at("/code").asInt() == 200) {
						// success return
						String strData = jsonResponseBody.at("/data").asText();
						if (strData == null || "".equals(strData)) {
							// body: no data
							bodyNode = new ObjectMapper().readTree("{}");
							
							if (Flag.flag) {
								// ret code
								int code = jsonResponseBody.at("/code").asInt();
								String message = jsonResponseBody.at("/message").asText();
								String strSearch = String.format("%d %s", code, message);
								// head
								LnsError lnsError = this.errorReaderJob.search(strSearch);
								((ObjectNode)headNode).put("resCode", lnsError.getError_code());
								((ObjectNode)headNode).put("resMessage", lnsError.getError_msg());
							}
						} else {
							// body
							String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();  // secretKey for data
							String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
							
							JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
							log.trace(">>>>> STEP-3 RES.jsonResponseData: " + jsonResponseData.toPrettyString());
							bodyNode = new ObjectMapper().readTree(jsonResponseData.toPrettyString());
							
							if (Flag.flag) {
								// head
								LnsError lnsError = this.errorReaderJob.search("SUCCESS");
								((ObjectNode)headNode).put("resCode", lnsError.getError_code());
								((ObjectNode)headNode).put("resMessage", lnsError.getError_msg());
							}
						}
						if (Flag.flag) {
							// error return
							lnsJsonNode.put("code", "00000");
							lnsJsonNode.put("status", "SUCCESS");
							lnsJsonNode.put("msgJson", "success to get return message");
						}
					} else {
						if (Flag.flag) {
							// res return code
							int code = jsonResponseBody.at("/code").asInt();
							String message = jsonResponseBody.at("/message").asText();
							String strSearch = String.format("%d %s", code, message);
							// head
							LnsError lnsError = this.errorReaderJob.search(strSearch);
							((ObjectNode)headNode).put("resCode", lnsError.getError_code());
							((ObjectNode)headNode).put("resMessage", lnsError.getError_msg());
						}
						if (Flag.flag) {
							// error return
							lnsJsonNode.put("code", "99910");
							lnsJsonNode.put("status", "ERROR");
							lnsJsonNode.put("msgJson", "success to get error message");
						}
					}
				} else {
					if (Flag.flag) {
						// head
						LnsError lnsError = this.errorReaderJob.search("ERROR");
						((ObjectNode)headNode).put("resCode", lnsError.getError_code());
						((ObjectNode)headNode).put("resMessage", lnsError.getError_msg());
					}
					if (Flag.flag) {
						// status error
						lnsJsonNode.put("code", "99920");
						lnsJsonNode.put("status", "ERROR");
						lnsJsonNode.put("msgJson", "System error StatusCode:" + response.getStatusCodeValue());
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				//int pos1 = message.indexOf('[');
				//int pos2 = message.lastIndexOf(']');
				if (Flag.flag) {
					lnsJsonNode.put("code", "99999");
					lnsJsonNode.put("status", "EXCEPTION");
					//nsJsonNode.put("msgJson", message.substring(pos1 + 1, pos2));
					lnsJsonNode.put("msgJson", message);
				}
				if (Flag.flag) {
					// error
					LnsError lnsError = this.errorReaderJob.search(message);
					((ObjectNode)headNode).put("resCode", lnsError.getError_code());
					((ObjectNode)headNode).put("resMessage", lnsError.getError_msg());
				}
			}
			
			//////////////////////////////////////////////////////////////////////////////////////
			if (Flag.flag) {
				// RESULT
				JsonNode resNode = (JsonNode) new ObjectMapper().createObjectNode();
				((ObjectNode) resNode).set("__head_data", headNode);
				((ObjectNode) resNode).set("__body_data", bodyNode);
				//log.trace(">>>>> resNode: " + resNode.toPrettyString());
				lnsJsonNode.put("resJson", resNode.toPrettyString());
				log.trace(">>>>> STEP-3 RES.lnsJsonNode          = {}", lnsJsonNode.toPrettyString());
			}
		}
		
		if (Flag.flag) {
			log.trace("================== Sentbe FINISH: {} ===================\n\n\n", lnsJsonNode.getText("name"));
		}
		
		return lnsJsonNode;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Deprecated
	public LnsJson post(LnsJson lnsJson) throws Exception {
		return post(lnsJson, false);
	}
	
	@Deprecated
	public LnsJson post(LnsJson lnsJson, boolean flagAccessToken) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("================== Lightnet START: {} ===================", lnsJson.getName());
			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
		}
		
		//String path = "/hanwha/checkUser";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-1");
			
			log.trace(">>>>> STEP-1: reqJsonData: {}", lnsJson.getReqJsonData());
			
			String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();
			String encryptData = Aes256.encrypt(lnsJson.getReqJsonData(), pass);
			log.trace(">>>>> STEP-1: pass(secretKey): {}", pass);
			log.trace(">>>>> STEP-1: encryptData:     {}", encryptData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encryptData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			log.trace(">>>>> STEP-1 jsonPrettyBody:   {}", jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			log.trace(">>>>> STEP-1 jsonBody:         {}", jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-2");
			
			String url = lnsJson.getHttpUrl().substring(8);
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.projEnvParamProperties.getSentbeSecretKeyForHmac();        // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());                       // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);         // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			log.trace(">>>>> STEP-2 client-key       [" + this.projEnvParamProperties.getSentbeClientKey() + "]");
			log.trace(">>>>> STEP-2 secret-key       [" + key + "]");
			log.trace(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			log.trace(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			log.trace(">>>>> STEP-2 url              [" + url + "]");
			log.trace(">>>>> STEP-2 body             [" + body + "]");
			log.trace(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			log.trace(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			log.trace(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			log.trace(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.trace("KANG-20200721 >>>>> STEP-3");
			
			String httpUrl = lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.POST;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.projEnvParamProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			log.trace(">>>>> STEP-3 reqHeaders: {}", JsonPrint.getInstance().toPrettyJson(reqHeaders));
			log.trace(">>>>> STEP-3 body:       {}", body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			log.trace(">>>>> STEP-3 reqHttpEntity: {}", reqHttpEntity);
			
			ResponseEntity<String> response = null;
			try {
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				log.trace(">>>>> STEP-3 RES.httpUrl              = {}", httpUrl);
				log.trace(">>>>> STEP-3 RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.trace(">>>>> STEP-3 RES.getStatusCode()      = {}", response.getStatusCode());
				log.trace(">>>>> STEP-3 RES.getBody()            = {}", response.getBody());
				
				if (response.getStatusCodeValue() == 200) {
					JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
					log.trace(">>>>> STEP-3 RES.response.getBody(): {}", jsonResponseBody.toPrettyString());
					
					/*
					if (jsonResponseBody.at("/code").asInt() == 200 && !"".equals(jsonResponseBody.at("/data").asText())) {
						String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();  // secretKey for data
						String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
						
						JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
						lnsJson.setResJsonData(jsonResponseData.toPrettyString());
						log.trace(">>>>> STEP-3 RES.getResJsonData: " + lnsJson.getResJsonData());
					}
					*/
					
					if (jsonResponseBody.at("/code").asInt() == 200) {
						String strData = jsonResponseBody.at("/data").asText();
						if (strData == null || "".equals(strData)) {
							lnsJson.setResJsonData("{}");
						} else {
							String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();  // secretKey for data
							String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
							
							JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
							lnsJson.setResJsonData(jsonResponseData.toPrettyString());
						}
					} else {
						lnsJson.setResJsonData("{}");
					}
					log.trace(">>>>> STEP-3 RES.getResJsonData: " + lnsJson.getResJsonData());
				}
				
				log.trace(">>>>> STEP-3 RES.lnsJson              = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
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
