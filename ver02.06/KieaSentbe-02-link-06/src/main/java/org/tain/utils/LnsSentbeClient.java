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
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsNodeTools;
import org.tain.object.lns.LnsJson;
import org.tain.properties.ProjEnvParamProperties;
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
			log.trace(">>>>> REQ.httpUrl(method): {} ({})", lnsJsonNode.getValue("httpUrl"), lnsJsonNode.getValue("httpMethod"));
			
			jsonNode = new ObjectMapper().readTree(lnsJsonNode.getValue("reqJson"));
			headNode = jsonNode.at("/__head_data");
			bodyNode = jsonNode.at("/__body_data");
			
			jsonHeadNode = headNode.toPrettyString();
			log.trace("KANG-20200721 >>>>> jsonHeadNode = {}", jsonHeadNode);
			
			jsonBodyNode = bodyNode.toPrettyString();
			log.trace("KANG-20200721 >>>>> jsonBodyNode = {}", jsonBodyNode);
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////
		LnsJsonNode reqJsonNode = new LnsJsonNode(lnsJsonNode.getJsonNode("request"));
		log.info(">>>>> POST.REQ.reqJsonNode    = {}", reqJsonNode.toPrettyString());
		LnsJsonNode reqHeadNode = new LnsJsonNode(reqJsonNode.getJsonNode("__head_data"));
		log.info(">>>>> POST.REQ.reqHeadNode    = {}", reqHeadNode.toPrettyString());
		LnsJsonNode reqBodyNode = new LnsJsonNode(reqJsonNode.getJsonNode("__body_data"));
		log.info(">>>>> POST.REQ.reqBodyNode    = {}", reqBodyNode.toPrettyString());
		
		//////////////////////////////////////////////////////////////////////////////////////////
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
			String url = "/" + StringTools.getHttpPath(lnsJsonNode.getValue("httpUrl"));
			
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
			
			String httpUrl = lnsJsonNode.getValue("httpUrl");
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
			
			//////////////////////////////////////////////////////////////////////////
			// response json
			LnsJsonNode resJsonNode = new LnsJsonNode("{}");
			LnsJsonNode resHeadNode = new LnsJsonNode(reqHeadNode.get().toString());  // head
			LnsJsonNode resDataNode = new LnsJsonNode("{}");                          // body
			
			ResponseEntity<String> response = null;
			try {
				if (Flag.flag) {
					// head
					resHeadNode.put("reqres", "0210");
					resHeadNode.put("resTime", LnsNodeTools.getTime());
				}
				
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				log.trace(">>>>> STEP-3 RES.httpUrl              = {}", httpUrl);
				log.trace(">>>>> STEP-3 RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.trace(">>>>> STEP-3 RES.getStatusCode()      = {}", response.getStatusCode());
				log.trace(">>>>> STEP-3 RES.getBody()            = {}", response.getBody());
				
				String strResJson = null;
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
							//lnsJson.setResJsonData("{}");
							strResJson = "{}";
						} else {
							String pass = this.projEnvParamProperties.getSentbeSecretKeyForData();  // secretKey for data
							String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
							
							JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
							//lnsJson.setResJsonData(jsonResponseData.toPrettyString());
							strResJson = jsonResponseData.toPrettyString();
						}
					} else {
						//lnsJson.setResJsonData("{}");
						strResJson = "{}";
					}
					log.trace(">>>>> STEP-3 RES.strResJson: " + strResJson);
					
					if (Flag.flag) {
						// res head
						((ObjectNode)headNode).put("reqres", "0710");
						((ObjectNode)headNode).put("resTime", StringTools.getHHMMSS());
						((ObjectNode)headNode).put("resCode", "00000");
						((ObjectNode)headNode).put("resMessage", "SUCCESS");
						
						// res body
						bodyNode = new ObjectMapper().readTree(strResJson);
						
						// res json
						JsonNode resNode = (JsonNode) new ObjectMapper().createObjectNode();
						((ObjectNode) resNode).set("__head_data", headNode);
						((ObjectNode) resNode).set("__body_data", bodyNode);
						log.trace(">>>>> resNode: " + resNode.toPrettyString());
						
						// lnsJsonNode set of res
						lnsJsonNode.put("resJson", resNode.toPrettyString());
					}
					
					if (Flag.flag) {
						// change reqResType
						String reqResType = lnsJsonNode.getValue("reqResType");
						reqResType = "0710" + reqResType.substring(4);
						lnsJsonNode.put("reqResType", reqResType);
					}
				}
				
				lnsJsonNode.put("code", "00000");
				lnsJsonNode.put("status", "SUCCESS");
				lnsJsonNode.put("msgJson", "success");
				
				log.trace(">>>>> STEP-3 RES.lnsJsonNode          = {}", lnsJsonNode.toPrettyString());
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJsonNode.put("code", "99999");
				lnsJsonNode.put("status", "FAIL");
				lnsJsonNode.put("msgJson", message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Flag.flag) {
			log.trace("================== Sentbe FINISH: {} ===================\n\n\n", lnsJsonNode.getValue("name"));
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
