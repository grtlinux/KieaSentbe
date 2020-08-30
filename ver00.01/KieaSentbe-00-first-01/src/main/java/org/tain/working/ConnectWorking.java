package org.tain.working;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.tain.object.user.User;
import org.tain.properties.LnsEnvJobProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.RestTemplateConfig;
import org.tain.utils.StbCrypt;
import org.tain.utils.enums.RestTemplateType;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.mervick.aes_everywhere.Aes256;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConnectWorking {

	@Autowired
	private LnsEnvJobProperties lnsEnvJobProperties;

	public void testStbCrypt() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String plainText = "저를 암호화 해주세요.";
			String encode = StbCrypt.getInstance().encrypt(plainText);
			String decode = StbCrypt.getInstance().decrypt(encode);
			
			System.out.println("============================================");
			System.out.println(">>>>> plainText [" + plainText+ "]");
			System.out.println(">>>>> encode    [" + encode+ "]");
			System.out.println(">>>>> decode    [" + decode+ "]");
			System.out.println("============================================");
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void getCalculation() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path = "/hanwha/getCalculation";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-1");
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("input_amount", 1000000);
			mapData.put("input_currency", "KRW");
			mapData.put("from_currency", "KRW");
			mapData.put("to_currency", "PHP");
			mapData.put("to_country", "PH");
			//mapData.put("exchange_rate_id", 1905202000);
			//if (Flag.flag) JsonPrint.getInstance().printPrettyJson(mapData);
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 secret-key       [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-3");
			
			String https = "https://hanwha.dev.sentbe.com:10443" + path;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			if (Flag.flag) System.out.println(">>>>> body: " + body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> https = {}", https);
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				if (jsonResponseBody.at("/code").asInt() == 200) {
					String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
					String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
					
					JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
					if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void createUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path = "/hanwha/createUser";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-1");
			
			User user = new User();
			String jsonData = JsonPrint.getInstance().toPrettyJson(user);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 secret-key       [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-3");
			
			String https = "https://hanwha.dev.sentbe.com:10443" + path;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			if (Flag.flag) System.out.println(">>>>> body: " + body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> https = {}", https);
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				if (jsonResponseBody.at("/code").asInt() == 200) {
					String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
					String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
					
					JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
					if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/*
	 * https://welcome.sentbe.com:10443/user?webviewId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg4NjEyNDYsImp0aSI6ImJ0NWxzZmdzajVvaTM4b2FvOXEwIiwidXNlcl9pZCI6MTAwMTA4OH0.09AvAURcAl-xoHbQRZj64_JD9PHa3QLH46LbaJpfUyk
	 * 가입 및 인증: welcome.sentbe.com/user?webviewId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg4NjEyNDYsImp0aSI6ImJ0NWxzZmdzajVvaTM4b2FvOXEwIiwidXNlcl9pZCI6MTAwMTA4OH0.09AvAURcAl-xoHbQRZj64_JD9PHa3QLH46LbaJpfUyk
	 * 
	 * 가입 및 인증: welcome.sentbe.com/user?webviewId={jwt}
	 * 송금 내역: welcome.sentbe.com/transfer_list?webviewId={jwt}
	 * 송금 신청: welcome.sentbe.com/transfer?webviewId={jwt}
	 * 
	 */
	public void getWebviewId() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path = "/hanwha/getWebviewId";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-1");
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("user_id", 1001088);
			//mapData.put("input_amount", 1000000.0);
			//mapData.put("input_currency", "KRW");
			//mapData.put("from_currency", "KRW");
			//mapData.put("to_currency", "PHP");
			//mapData.put("to_country", "PH");
			//mapData.put("exchange_rate_id", 1905202000);
			//if (Flag.flag) JsonPrint.getInstance().printPrettyJson(mapData);
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 secret-key       [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-3");
			
			String https = "https://hanwha.dev.sentbe.com:10443" + path;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			if (Flag.flag) System.out.println(">>>>> body: " + body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> https = {}", https);
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				if (jsonResponseBody.at("/code").asInt() == 200) {
					String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
					String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
					
					JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
					if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void checkUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path = "/hanwha/checkUser";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-1");
			
			Map<String,Object> mapData = new HashMap<>();
			//mapData.put("id_number", "701225-1234567");
			mapData.put("id_number", "7012251234567");
			//if (Flag.flag) JsonPrint.getInstance().printPrettyJson(mapData);
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 secret-key       [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-3");
			
			String https = "https://hanwha.dev.sentbe.com:10443" + path;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			if (Flag.flag) System.out.println(">>>>> body: " + body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> https = {}", https);
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				if (jsonResponseBody.at("/code").asInt() == 200) {
					String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
					String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
					
					JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
					if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
				}
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void deleteUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path = "/hanwha/deleteUser";
		String body = null;
		String nonce = null;
		String signature = null;
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-1");
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("user_id", 82128);
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 secret-key       [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> STEP-2 signature Base64 [" + signatureBase64 + "]");
			
			signature = signatureBase64;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-3");
			
			String https = "https://hanwha.dev.sentbe.com:10443" + path;
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signature);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			if (Flag.flag) System.out.println(">>>>> body: " + body);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(body, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> https = {}", https);
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				if (jsonResponseBody.at("/code").asInt() == 200) {
					String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
					String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
					
					JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
					if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
				}
			}
		}
	}
}
