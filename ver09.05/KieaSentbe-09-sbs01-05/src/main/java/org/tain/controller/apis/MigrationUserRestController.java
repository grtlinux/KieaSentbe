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
import org.tain.object.lns.LnsJson;
import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsRecvQueue;
import org.tain.queue.LnsSendQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = {"/sbs01/migrationUser"})
@Slf4j
public class MigrationUserRestController {
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	public LnsRecvQueue lnsRecvQueue = new LnsRecvQueue();
	
	@RequestMapping(value = {""}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> migrationUser(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("Lns01/MigrationUser >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("Lns01/MigrationUser >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsStream reqLnsStream = null;
			if (Flag.flag) {
				String reqStrData = lnsJson.getReqStrData();
				String reqTypeCode = "0700800";
				String reqLen = String.format("%04d", 7 + reqStrData.length());
				reqLnsStream = new LnsStream(reqLen + reqTypeCode + reqStrData);
				log.info("Lns01/MigrationUser >>>>> reqLnsStream = {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
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
				log.info("Lns01/MigrationUser >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
			}
			
			lnsJson.setResStrData(resLnsStream.getContent());
			log.info("Lns01/MigrationUser  >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
}
