package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsRecvQueue;
import org.tain.queue.LnsSendQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/sbs01"})
@Slf4j
public class ApisRestController {
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	public LnsRecvQueue lnsRecvQueue = new LnsRecvQueue();
	
	@RequestMapping(value = {"/process"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> getCalculation(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		String strBody = null;
		if (Flag.flag) {
			strBody = reqHttpEntity.getBody();
			log.info("SBS01 >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("SBS01 >>>>> Body = {}", strBody);
		}
		
		JsonNode jsonNode = null;
		if (Flag.flag) {
			jsonNode = new ObjectMapper().readTree(strBody);
			
			LnsStream reqLnsStream = null;
			if (Flag.flag) {
				String reqStrData = jsonNode.get("reqStream").asText();
				reqLnsStream = new LnsStream(reqStrData);
				log.info("SBS01 >>>>> reqLnsStream = {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
			}
			
			if (Flag.flag) {
				LnsQueueObject lnsQueueObject = LnsQueueObject.builder()
						.lnsStream(reqLnsStream)
						.lnsRecvQueue(this.lnsRecvQueue)
						.build();
				this.lnsSendQueue.set(lnsQueueObject);
			}
			
			LnsStream resLnsStream = null;
			if (Flag.flag) {
				resLnsStream = (LnsStream) this.lnsRecvQueue.get();
				log.info("SBS01 >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
			}
			
			((ObjectNode) jsonNode).put("resStream", resLnsStream.getData());
			log.info("SBS01  >>>>> jsonNode = {}", jsonNode.toPrettyString());
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(jsonNode.toPrettyString(), headers, HttpStatus.OK);
	}
}
