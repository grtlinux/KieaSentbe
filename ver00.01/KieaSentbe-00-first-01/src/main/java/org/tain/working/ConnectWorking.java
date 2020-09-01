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
	
	/*
	 * name: getCalculation
	 * 
	 * REQ: {
	 *   "input_amount": 1000000, // [필수]입력 금액
	 *   "input_currency": "KRW", // [필수]입력 통화
	 *   "from_currency": "KRW", // [필수]보내는 통화
	 *   "to_currency": "PHP", // [필수]받는 통화
	 *   "to_country": "PH", // [필수]받는 국가
	 *   "exchange_rate_id": 20190605175000 // [옵션] 환율 ID
	 * }
	 * RES: {
	 *   "exchange_rate_id": "20190605175000", //환율 ID
	 *   "from_amount": 1000000, // 보내는 금액
	 *   "from_currency": "KRW", // 보내는 통화
	 *   "to_amount": 43258, // 받는 금액
	 *   "to_currency": "PHP", // 받는 통화
	 *   "transfer_fee": 5000 // 송금 수수료
	 * }
	 */
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
			mapData.put("exchange_rate_id", "20190605175000");
			
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
	
	/*
	 * name: createUser
	 * 
	 * REQ: {
	 *   "agreements": { // 동의 약관
	 *     "version": 1, // 동의 약관 version
	 *     "list": [
	 *       "SSFCTS", // 소액해외송금약관
	 *       "EFTS", // 전자금융거래이용약관
	 *       "PRIVACY", [옵션] // 개인정보처리방침 약관
	 *       "CAUTIONS", // 고유식별정보 약관
	 *       "CLAIMS" // 개인정보 제3자 제공
	 *     ]
	 *   },
	 *   "phone": {
	 *     "iso": "KR", // [옵션] 전화번호 국제 코드 (ISO)
	 *     "number": 1012345678 // 전화번호
	 *   },
	 *   "user_name": { // 영문이름
	 *     "first": "Jiwon", // 이름
	 *     "middle": "", // [옵션] 중간이름
	 *     "last": "Tak" // 성
	 *   },
	 *   "gender": 1, // 성별   남(1), 여(2)
	 *   "account_number": "05012345678900", // 계좌번호
	 *   "account_holder_name": "탁지원", // 계좌주명
	 *   "birth_date": "19701225", // 생년월일(YYYYMMDD)
	 *   "nationality_iso": "KR", // 국적 (ISO)
	 *   "id_number": "701225-1234567", // 실명번호 
	 *   "email": "email@sentbe.com", // [옵션] 이메일
	 *   "often_send_country_iso": "PH", // 주송금 국가(ISO)
	 *   "occupation": 3, // 직업 (규격정의)
	 *   "funds_source": 3, // 자원출처 (규격정의)
	 *   "transfer_purpose": 3 // 송금목적 (규격정의)
	 * }
	 * 
	 * RES: {
	 *   "user_id": "82163", // 유저 ID
	 *   "webview_id": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NjAzMjUxNDgsImp0aSI6ImJqdmxsNzc4a2kzOGxsam5xN2owIiwidXNlcl9pZCI6ODIxNjN9.r47Dd_Xdo5pGmGbIg-1gCdtnP7BqVg6IQM7QCK1bNG4" // 웹뷰 ID
	 * }
	 */
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
			//String jsonData = JsonPrint.getInstance().toJson(user);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonData: " + jsonData);
			
			String pass = this.lnsEnvJobProperties.getSentbeSecretKeyForData();  // secretKey for data
			String encrypData = Aes256.encrypt(jsonData, pass);                  // Encryption
			if (Flag.flag) System.out.println(">>>>> STEP-1 pass(secretKey): " + pass);
			if (Flag.flag) System.out.println(">>>>> STEP-1 encrypData     : " + encrypData);
			
			Map<String,String> mapReq = new HashMap<>();
			mapReq.put("data", encrypData);
			String jsonPrettyBody = JsonPrint.getInstance().toPrettyJson(mapReq);
			//String jsonPrettyBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonPrettyBody: " + jsonPrettyBody);
			
			String jsonBody = JsonPrint.getInstance().toJson(mapReq);
			if (Flag.flag) System.out.println(">>>>> STEP-1 jsonBody: " + jsonBody);
			
			body = jsonPrettyBody;
		}
		
		if (Flag.flag) {
			log.info("KANG-20200721 >>>>> STEP-2");
			
			String url = "hanwha.dev.sentbe.com:10443" + path;
			long epochTime = System.currentTimeMillis();
			//nonce = String.valueOf(epochTime / 1000);
			nonce = String.valueOf(epochTime);
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
	 * name: getWebviewId
	 * 
	 * REQ: {
	 *   "user_id": "82161" // 유저 ID
	 * }
	 * 
	 * RES: {
	 *   "user_id": "82150",
	 *   "webview_id": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiwidXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI"
	 * }
	 */
	
	/*
	 * - 가입 및 인증 URL
	 * https://hanwha.dev.sentbe.com/user?webviewId={webviewId}
	 * 
	 * ex: https://hanwha.dev.sentbe.com/user?webviewId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg5NDUzNTYsImp0aSI6ImJ0NmFkajYybzdubmpuYzdhb2lnIiwidXNlcl9pZCI6MTAwMTA5OX0.INZTvkI51YCQm_TQF4KrWrNoCKm8wTUDOh5oU8w_ilo
	 *    
	 * - 송금 내역 URL
	 * https://hanwha.dev.sentbe.com/transfer_list?webviewId={webviewId}
	 * 
	 * ex: https://hanwha.dev.sentbe.com/transfer_list?webviewId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg5NDUzNTYsImp0aSI6ImJ0NmFkajYybzdubmpuYzdhb2lnIiwidXNlcl9pZCI6MTAwMTA5OX0.INZTvkI51YCQm_TQF4KrWrNoCKm8wTUDOh5oU8w_ilo
	 * 
	 * - 송금 신청 URL
	 * https://hanwha.dev.sentbe.com/transfer?webviewid={webviewId}&exchange_rate_id={exchange_rate_id}&send_amount={send_amount}&to_country={to_country}&to_currency${to_currency}
	 * exchange_rate_id 는 Optional.
	 * 
	 * ex: https://hanwha.dev.sentbe.com/transfer?webviewId=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg5NDUzNTYsImp0aSI6ImJ0NmFkajYybzdubmpuYzdhb2lnIiwidXNlcl9pZCI6MTAwMTA5OX0.INZTvkI51YCQm_TQF4KrWrNoCKm8wTUDOh5oU8w_ilo&to_country=CN&to_currency=CNY&send_amount=300000
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
	
	/*
	 * name: checkUser
	 * 
	 * REQ: {
	 *   "id_number": "701225-1234567"
	 * }
	 * 
	 * RES: {
	 *   "id_number": "701225-1234567",
	 *   "sign_status": true,
	 *   "account_number": "05012345678900"
	 * }
	 */
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
	
	/*
	 * name: deleteUser
	 * 
	 * REQ: {
	 *   "user_id": "82161" // 유저 ID
	 * }
	 * 
	 * RES: {
	 *     "error": 200,
	 *     "code": 200,
	 *     "message": "",
	 *     "data": ""
	 * }
	 */
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

