package org.tain.utils;

//@Component
//@Slf4j
public class LnsLightnetClient {

	/*
	@Autowired
	private LnsData lnsData;
	
	@Autowired
	private ErrorReaderJob errorReaderJob;
	
	///////////////////////////////////////////////////////////////////////////
	
	public LnsJsonNode get(LnsJsonNode lnsJson) throws Exception {
		return get(lnsJson, true);
	}
	
	public LnsJsonNode get(LnsJsonNode lnsJsonNode, boolean flagAccessToken) throws Exception {
		
		log.info("\n\n\n=========================== LnsLightnetClient.GET START =============================");
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info(">>>>> GET.REQ.lnsJsonNode: {}", lnsJsonNode.toPrettyString());
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJsonNode.getText("httpUrl");
			HttpMethod httpMethod = HttpMethod.GET;
			
			LnsJsonNode reqJsonNode = new LnsJsonNode(lnsJsonNode.getJsonNode("request"));
			log.info(">>>>> GET.REQ.reqJsonNode    = {}", reqJsonNode.toPrettyString());
			LnsJsonNode reqHeadNode = new LnsJsonNode(reqJsonNode.getJsonNode("__head_data"));
			log.info(">>>>> GET.REQ.reqHeadNode    = {}", reqHeadNode.toPrettyString());
			LnsJsonNode reqBodyNode = new LnsJsonNode(reqJsonNode.getJsonNode("__body_data"));
			log.info(">>>>> GET.REQ.reqBodyNode    = {}", reqBodyNode.toPrettyString());
			
			Map<String,String> reqMap = new ObjectMapper().readValue(reqBodyNode.toPrettyString(), new TypeReference<Map<String,String>>(){});
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.setAll(reqMap);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(httpUrl)
					.queryParams(map)
					.build(true);
			httpUrl = builder.toString();
			log.info(">>>>> GET.REQ.httpUrl = {}", httpUrl);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			if (flagAccessToken) reqHeaders.set("Authorization", "Bearer " + this.lnsData.getAccessToken());
			log.info(">>>>> GET.REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(reqHeaders);
			log.info(">>>>> GET.REQ.reqHttpEntity  = {}", reqHttpEntity);
			
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
				
				log.info(">>>>> GET.RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.info(">>>>> GET.RES.getStatusCode()      = {}", response.getStatusCode());
				log.info(">>>>> GET.RES.getBody()            = {}", response.getBody());
				
				lnsJsonNode.put("code", "00000");
				lnsJsonNode.put("status", "success");
				lnsJsonNode.put("message", "OK");
				
				if (Flag.flag) {
					// head
					LnsError lnsError = this.errorReaderJob.search("SUCCESS");
					resHeadNode.put("resCode", lnsError.getError_code());
					resHeadNode.put("resMessage", "SUCCESS");
					
					// body
					resDataNode = new LnsJsonNode(response.getBody());
				}
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJsonNode.put("code", "99999");
				lnsJsonNode.put("status", "fail");
				lnsJsonNode.put("message", message.substring(pos1 + 1, pos2));
				if (Flag.flag) {
					// head
					LnsError lnsError = this.errorReaderJob.search(message);
					resHeadNode.put("resCode", lnsError.getError_code());
					resHeadNode.put("resMessage", lnsError.getError_msg());
				}
			}
			
			if (Flag.flag) {
				// lnsJsonNode  response
				resJsonNode.put("__head_data", resHeadNode.get());
				resJsonNode.put("__body_data", resDataNode.get());
				lnsJsonNode.put("response", resJsonNode.get());
			}
		}
		
		if (Flag.flag) {
			log.info(">>>>> GET.RES.lnsJsonNode: {}", lnsJsonNode.toPrettyString());
		}
		
		log.info("--------------------------- LnsLightnetClient.GET END -----------------------------\n\n\n");
		
		return lnsJsonNode;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public LnsJsonNode post(LnsJsonNode lnsJson) throws Exception {
		return post(lnsJson, true);
	}
	
	public LnsJsonNode post(LnsJsonNode lnsJsonNode, boolean flagAccessToken) throws Exception {
		
		log.info("\n\n\n=========================== LnsLightnetClient.POST START =============================");
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info(">>>>> POST.REQ.lnsJsonNode: {}", lnsJsonNode.toPrettyString());
		}
		
		if (Flag.flag) {
			String httpUrl = lnsJsonNode.getText("httpUrl");
			HttpMethod httpMethod = HttpMethod.POST;
			
			LnsJsonNode reqJsonNode = new LnsJsonNode(lnsJsonNode.getJsonNode("request"));
			log.info(">>>>> POST.REQ.reqJsonNode    = {}", reqJsonNode.toPrettyString());
			LnsJsonNode reqHeadNode = new LnsJsonNode(reqJsonNode.getJsonNode("__head_data"));
			log.info(">>>>> POST.REQ.reqHeadNode    = {}", reqHeadNode.toPrettyString());
			LnsJsonNode reqBodyNode = new LnsJsonNode(reqJsonNode.getJsonNode("__body_data"));
			log.info(">>>>> POST.REQ.reqBodyNode    = {}", reqBodyNode.toPrettyString());
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			if (flagAccessToken) reqHeaders.set("Authorization", "Bearer " + this.lnsData.getAccessToken());
			log.info(">>>>> POST.REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(reqBodyNode.toPrettyString(), reqHeaders);
			log.info(">>>>> POST.REQ.reqHttpEntity  = {}", reqHttpEntity);
			
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
				
				log.info(">>>>> POST.RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				log.info(">>>>> POST.RES.getStatusCode()      = {}", response.getStatusCode());
				log.info(">>>>> POST.RES.getBody()            = {}", response.getBody());
				
				lnsJsonNode.put("code", "00000");
				lnsJsonNode.put("status", "success");
				lnsJsonNode.put("message", "OK");
				
				if (Flag.flag) {
					// head
					LnsError lnsError = this.errorReaderJob.search("SUCCESS");
					resHeadNode.put("resCode", lnsError.getError_code());
					resHeadNode.put("resMessage", "SUCCESS");
					
					// body
					resDataNode = new LnsJsonNode(response.getBody());
				}
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				lnsJsonNode.put("code", "99999");
				lnsJsonNode.put("status", "fail");
				lnsJsonNode.put("message", message.substring(pos1 + 1, pos2));
				if (Flag.flag) {
					// head
					LnsError lnsError = this.errorReaderJob.search(message);
					resHeadNode.put("resCode", lnsError.getError_code());
					resHeadNode.put("resMessage", lnsError.getError_msg());
				}
			}
			
			if (Flag.flag) {
				// lnsJsonNode  response
				resJsonNode.put("__head_data", resHeadNode.get());
				resJsonNode.put("__body_data", resDataNode.get());
				lnsJsonNode.put("response", resJsonNode.get());
			}
		}
		
		if (Flag.flag) {
			log.info(">>>>> POST.RES.lnsJsonNode: {}", lnsJsonNode.toPrettyString());
		}
		
		log.info("--------------------------- LnsLightnetClient.POST END -----------------------------\n\n\n");
		
		return lnsJsonNode;
	}
	*/
}
