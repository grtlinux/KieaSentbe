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
import org.tain.properties.LnsEnvJobProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.RestTemplateConfig;
import org.tain.utils.StbCrypt;
import org.tain.utils.enums.RestTemplateType;

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
	
	@Deprecated
	public void getCalculation0() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String https = "https://hanwha.dev.sentbe.com:10443/getCalculation";
		long epochTime = System.currentTimeMillis();
		String nonce = String.valueOf(epochTime / 1000);
		String signatureHexit = null;
		String signatureBase64 = null;
		
		Map<String,String> mapReq = null;
		String jsonBody = null;
		if (Flag.flag) {
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("input_amount", 1000000);
			mapData.put("input_currency", "KRW");
			mapData.put("from_currency", "KRW");
			mapData.put("to_currency", "PHP");
			mapData.put("to_country", "PH");
			mapData.put("exchange_rate_id", 1905202000);
			
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			String pass = this.lnsEnvJobProperties.getSentbeSecretKey();  // Secret-Key
			String encrypData = Aes256.encrypt(jsonData, pass);
			if (Flag.flag) System.out.println(">>>>> jsonData: " + jsonData);
			if (Flag.flag) System.out.println(">>>>> encrypData: " + encrypData);
			
			mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			//String jsonBdoy = JsonPrint.getInstance().toPrettyJson(mapReq);
			jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> jsonBody: " + jsonBody);
		}
		
		if (Flag.flag) {
			String url = "hanwha.dev.sentbe.com:10443/getWebviewId";
			String body = jsonBody;
			String message = nonce + url + body;
			String key = this.lnsEnvJobProperties.getSentbeSecretKey();  // Secret-Key
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));   // Secret-Key
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> 0. https            [" + https + "]");
			if (Flag.flag) System.out.println(">>>>> 0. client-key       [" + this.lnsEnvJobProperties.getSentbeClientKey() + "]");
			if (Flag.flag) System.out.println(">>>>> 0. secret-key       [" + this.lnsEnvJobProperties.getSentbeSecretKey() + "]");
			if (Flag.flag) System.out.println(">>>>> 0. epochTime(millisec)   [" + epochTime + "]");
			if (Flag.flag) System.out.println(">>>>> 1. nonce(epochTime/1000) [" + nonce + "]");
			if (Flag.flag) System.out.println(">>>>> 2. url              [" + url + "]");
			if (Flag.flag) System.out.println(">>>>> 3. body             [" + body + "]");
			if (Flag.flag) System.out.println(">>>>> 4. messge(1+2+3)    [" + message + "]");
			if (Flag.flag) System.out.println(">>>>> 5. key(secret-key)  [" + key + "]");
			if (Flag.flag) System.out.println(">>>>> 6. signature Hexit  [" + signatureHexit + "]");
			if (Flag.flag) System.out.println(">>>>> 6. signature Base64 [" + signatureBase64 + "]");
		}
		
		if (Flag.flag) {
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			//reqHeaders.set("x-api-nonce", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
			reqHeaders.set("x-api-nonce", nonce);
			reqHeaders.set("x-api-signature", signatureBase64);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("ReqHeaders", reqHeaders);
			
			HttpEntity<Map<String,String>> reqHttpEntity = new HttpEntity<>(mapReq, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					https
					, HttpMethod.POST
					, reqHttpEntity
					, String.class);
					
			log.info("=====================================================");
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private String clientKey = "Jien4aeyae7ohjeithahM9ja7quowua4";
	private String secretKeyForData = "cXdqZmlvcWVqd2xd2pmam9pZaG9nZnFl";
	private String secretKeyForHmac = "6fc1bf16f3cb6f5b6a1044fd9edecba9722014489c39a7d16ad7b9961aea770b9cd0e3e4a85510bba0dd97e6d6aac884d910e6384c7b76df992deece5cb57a78";

	public void getCalculation1() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
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
			mapData.put("exchange_rate_id", 1905202000);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson(mapData);
			String jsonData = JsonPrint.getInstance().toJson(mapData);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.secretKeyForData;                 // Secret-Key
			String encrypData = Aes256.encrypt(jsonData, pass);  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443/hanwha/getCalculation";
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			//nonce = "1598507516";
			String message = nonce + url + body;
			String key = this.secretKeyForHmac;                             // Secret-Key
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			String signatureHexit = DatatypeConverter.printHexBinary(hash);     // Hexit
			String signatureBase64 = DatatypeConverter.printBase64Binary(hash);     // Base64
			
			if (Flag.flag) System.out.println(">>>>> STEP-2 client-key       [" + this.clientKey + "]");
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
			
			String https = "https://hanwha.dev.sentbe.com:10443/hanwha/getCalculation";
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.clientKey);
			//reqHeaders.set("x-api-nonce", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
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
			log.info("KANG-20200623 >>>>> response.getStatusCodeValue() = {}", response.getStatusCodeValue());
			log.info("KANG-20200623 >>>>> response.getStatusCode()      = {}", response.getStatusCode());
			log.info("KANG-20200623 >>>>> response.getBody()            = {}", response.getBody());
			log.info("=====================================================");
			if (response.getStatusCodeValue() == 200) {
			}
		}
	}
	
	@Deprecated
	public void getCalculation2() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			/*
			log.info("KANG-20200721 >>>>> STEP-1");
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("input_amount", 1000000);
			mapData.put("input_currency", "KRW");
			mapData.put("from_currency", "KRW");
			mapData.put("to_currency", "PHP");
			mapData.put("to_country", "PH");
			mapData.put("exchange_rate_id", 1905202000);
			String jsonData = JsonPrint.getInstance().toPrettyJson(mapData);
			if (Flag.flag) System.out.println(">>>>> jsonData: " + jsonData);
			
			String pass = this.secretKey2;                       // Secret-Key
			String encrypData = Aes256.encrypt(jsonData, pass);  // Encryption
			if (Flag.flag) System.out.println(">>>>> pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> jsonBody: " + jsonBody);
			*/
		}
	}
}
/*
curl -XPOST -H 'x-api-nonce: 1598496014' -H 'x-api-signature: jb5wFA1hcnJwhxKzZRQF2ysNLxt+tXNNH/kIQfwIbf4=' -d '{"data":"U2FsdGVkX18sjpt39HJnhCdqHFjfh9FMHllT1VvDFi59voF0XhfABgTvoWnnWKGX0H3uHMIvcm/dK7TsdsIOz5HWbCztKfIabvwarn36cTkPMEG/Lmio3XRhG8YU1UV7S1VepIOnVaWvWbbtDa+q1fUoEMvv7vEC3aZUntfV8EpIjZaWFEIgQcEvS6avrtAYpRU2tYPEJHf7W41ccCkyjUH0C44igkUhUn/jEqNA/iwDQCXKuIC9OpVYeHiUcwTR"}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
curl -XPOST -H 'x-api-nonce: 1598507516' -H 'x-api-signature: 9q/XPzFZHn2JUhqyUZGqIti/lCUUmF9pxIBxGEAonR8=' -d '{"data":"U2FsdGVkX1/ZBY9+cRQuZnh97wF550Ld489C2LYuVa+58ILabKMBMi0SGlz9Ghf+EDBVU71MDlNYgjKmxm+YNflZYuycevBeJqIeU4r9Sac/3FgZL3eOqUgV3mjqxLjNAuSzyrp+rKCyipSzy/5zAhLihG2+PzUsDaM2JoczBLiytMmlzF5+BMhr1lvlk9+w3HYWeutgV1iQAO/o00Vy5w=="}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
curl -XPOST -H 'x-api-nonce: 1598515410' -H 'x-api-signature: 3YXP02ylDopPxCtXFsblWBAW7rF6Q9QW/GMNDjctFpw=' -d '{"data":"U2FsdGVkX1/joN5EWZNZOec+T/zwU473493B5JHrW/4NKnFfZNQc00z5a+k8h1vTT1TjsRbA1u7PZcUIc8gpTa1jUIWLWMci4qza9juo2BqUfdClrTYxaQoDI6sEM4wmTs87faVWvUnoTnuq+4DCGKm9245U+BJErOhCaPBe3EFbNoTADA6JCQBCsW1Bmcez3jhJ5VTaV7Z53aGYN5A3zQ=="}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
 */
