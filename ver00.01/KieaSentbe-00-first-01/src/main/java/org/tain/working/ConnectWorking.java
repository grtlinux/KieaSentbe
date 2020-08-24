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
		
		String epochTime = String.valueOf(System.currentTimeMillis());
		String signature = null;
		
		if (Flag.flag) {
			String nonce = epochTime;
			String url = "rest.sentbe.com/getWebviewId";
			String body = "{\"message_key\":\"message_value\"}";
			String message = nonce + url + body;
			String key = this.lnsEnvJobProperties.getSentbeSecretKey();  // Secret-Key
			
			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));   // Secret-Key
			byte[] hash = hasher.doFinal(message.getBytes());               // message
			
			signature = DatatypeConverter.printHexBinary(hash);     // Hexit
			if (Flag.flag) System.out.println(">>>>> signature Hexit [" + signature + "]");
		}
		
		if (Flag.flag) {
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.set("Accept", "application/json");
			reqHeaders.set("Content-Type", "application/json; charset=utf-8");
			reqHeaders.set("x-api-key", this.lnsEnvJobProperties.getSentbeClientKey());
			//reqHeaders.set("x-api-nonce", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
			reqHeaders.set("x-api-nonce", epochTime);
			reqHeaders.set("x-api-signature", signature);
			
			Map<String,Object> mapData = new HashMap<>();
			mapData.put("input_amount", 1000000);
			mapData.put("input_currency", "KRW");
			mapData.put("from_currency", "KRW");
			mapData.put("to_currency", "PHP");
			mapData.put("to_country", "PH");
			mapData.put("exchange_rate_id", 1905202000);
			String jsonData = JsonPrint.getInstance().toJson(mapData);
			String encode = StbCrypt.getInstance().encrypt(jsonData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encode);
			
			HttpEntity<Map<String,String>> reqHttpEntity = new HttpEntity<>(mapReq, reqHeaders);
			
			ResponseEntity<String> response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
					//"https://hanwha.dev.sentbe.com:10443/getCalculation"
					//"https://hanhaw.dev.sentbe.com:10443/getCalculation"
					"https://dev.sentbe.com:10443/getCalculation"
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
