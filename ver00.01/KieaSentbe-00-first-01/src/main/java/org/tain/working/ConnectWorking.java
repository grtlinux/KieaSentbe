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
		
		String https = "https://hanwha.dev.sentbe.com:10443/getCalculation";
		long epochTime = System.currentTimeMillis();
		String nonce = String.valueOf(epochTime / 1000);
		String signatureHexit = null;
		String signatureBase64 = null;
		
		if (Flag.flag) {
			String url = "hanwha.dev.sentbe.com:10443/getWebviewId";
			String body = "{\"message_key\":\"message_value\"}";
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
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("input_amount", 1000000);
			mapData.put("input_currency", "KRW");
			mapData.put("from_currency", "KRW");
			mapData.put("to_currency", "PHP");
			mapData.put("to_country", "PH");
			mapData.put("exchange_rate_id", 1905202000);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("Request Date", mapData);
			String jsonData = JsonPrint.getInstance().toJson(mapData);
			
			Map<String,String> mapReq = new HashMap<>();
			String encode = StbCrypt.getInstance().encrypt(jsonData);
			mapReq.put("data", encode);
			if (Flag.flag) JsonPrint.getInstance().printPrettyJson("Request Body", mapReq);
			
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
}
