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
	
	public void getCalculation() throws Exception {
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
			
			String url = "hanwha.dev.sentbe.com:10443/hanwha/getCalculation";
			long epochTime = System.currentTimeMillis();
			nonce = String.valueOf(epochTime / 1000);
			String message = nonce + url + body;
			String key = this.lnsEnvJobProperties.getSentbeSecretKeyForHmac();   // secretKey for hmac
			
			Mac hasher = Mac.getInstance("HmacSHA256");
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
			
			String https = "https://hanwha.dev.sentbe.com:10443/hanwha/getCalculation";
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
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
				JsonNode jsonResponseBody = JsonPrint.getInstance().getObjectMapper().readTree(response.getBody());
				if (Flag.flag) System.out.println(">>>>> response.getBody(): " + jsonResponseBody.toPrettyString());
				
				String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
				String decryptData = Aes256.decrypt(jsonResponseBody.at("/data").asText(), pass);
				
				JsonNode jsonResponseData = JsonPrint.getInstance().getObjectMapper().readTree(decryptData);
				if (Flag.flag) System.out.println(">>>>> jsonResponseData: " + jsonResponseData.toPrettyString());
			}
		}
	}
}
/*
curl -XPOST -H 'x-api-nonce: 1598496014' -H 'x-api-signature: jb5wFA1hcnJwhxKzZRQF2ysNLxt+tXNNH/kIQfwIbf4=' -d '{"data":"U2FsdGVkX18sjpt39HJnhCdqHFjfh9FMHllT1VvDFi59voF0XhfABgTvoWnnWKGX0H3uHMIvcm/dK7TsdsIOz5HWbCztKfIabvwarn36cTkPMEG/Lmio3XRhG8YU1UV7S1VepIOnVaWvWbbtDa+q1fUoEMvv7vEC3aZUntfV8EpIjZaWFEIgQcEvS6avrtAYpRU2tYPEJHf7W41ccCkyjUH0C44igkUhUn/jEqNA/iwDQCXKuIC9OpVYeHiUcwTR"}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
curl -XPOST -H 'x-api-nonce: 1598507516' -H 'x-api-signature: 9q/XPzFZHn2JUhqyUZGqIti/lCUUmF9pxIBxGEAonR8=' -d '{"data":"U2FsdGVkX1/ZBY9+cRQuZnh97wF550Ld489C2LYuVa+58ILabKMBMi0SGlz9Ghf+EDBVU71MDlNYgjKmxm+YNflZYuycevBeJqIeU4r9Sac/3FgZL3eOqUgV3mjqxLjNAuSzyrp+rKCyipSzy/5zAhLihG2+PzUsDaM2JoczBLiytMmlzF5+BMhr1lvlk9+w3HYWeutgV1iQAO/o00Vy5w=="}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
curl -XPOST -H 'x-api-nonce: 1598515410' -H 'x-api-signature: 3YXP02ylDopPxCtXFsblWBAW7rF6Q9QW/GMNDjctFpw=' -d '{"data":"U2FsdGVkX1/joN5EWZNZOec+T/zwU473493B5JHrW/4NKnFfZNQc00z5a+k8h1vTT1TjsRbA1u7PZcUIc8gpTa1jUIWLWMci4qza9juo2BqUfdClrTYxaQoDI6sEM4wmTs87faVWvUnoTnuq+4DCGKm9245U+BJErOhCaPBe3EFbNoTADA6JCQBCsW1Bmcez3jhJ5VTaV7Z53aGYN5A3zQ=="}' 'https://hanwha.dev.sentbe.com:10443/getCalculation'
 */
