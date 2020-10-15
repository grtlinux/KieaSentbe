package org.tain.controller.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.mapper.LnsCStruct;
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsJsonToStream;
import org.tain.mapper.LnsMstInfo;
import org.tain.mapper.LnsStreamToJson;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/mapper"})
@Slf4j
public class ApisRestController {

	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.6/mapper/s2j
	 */
	@RequestMapping(value = {"/s2j"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> strToJson(HttpEntity<String> reqHttpEntity) throws Exception {
		if (Flag.flag) log.info("========================== START: mapper =========================");
		if (Flag.flag) log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("MAPPER.req >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.req >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode(reqHttpEntity.getBody());
		}
		if (Flag.flag) {
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get(lnsJsonNode.getValue("reqResType"));
			JsonNode node = new LnsStreamToJson(lnsMstInfo, lnsJsonNode.getValue("stream")).get();
			lnsJsonNode.put("json", node.toPrettyString());
			log.info("MAPPER.req >>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		if (Flag.flag) log.info("========================== END: mapper =========================");
		
		return new ResponseEntity<>(lnsJsonNode.toPrettyString(), headers, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.6/mapper/j2s
	 */
	@RequestMapping(value = {"/j2s"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> jsonToStr(HttpEntity<String> reqHttpEntity) throws Exception {
		if (Flag.flag) log.info("========================== START: mapper =========================");
		if (Flag.flag) log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("MAPPER.req >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.req >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode(reqHttpEntity.getBody());
		}
		if (Flag.flag) {
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get(lnsJsonNode.getValue("reqResType"));
			String stream = new LnsJsonToStream(lnsMstInfo, lnsJsonNode.getValue("json")).get();
			lnsJsonNode.put("stream", stream);
			log.info("MAPPER.req >>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		if (Flag.flag) log.info("========================== END: mapper =========================");
		
		return new ResponseEntity<>(lnsJsonNode.toPrettyString(), headers, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.6/mapper/cstruct
	 */
	@RequestMapping(value = {"/cstruct"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> cstruct(HttpEntity<String> reqHttpEntity) throws Exception {
		if (Flag.flag) log.info("========================== START: mapper =========================");
		if (Flag.flag) log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			log.info("MAPPER.cstruct >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.cstruct >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJsonNode lnsJsonNode = null;
		if (Flag.flag) {
			lnsJsonNode = new LnsJsonNode(reqHttpEntity.getBody());
		}
		if (Flag.flag) {
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get(lnsJsonNode.getValue("reqResType"));
			String strCStruct = new LnsCStruct(lnsMstInfo).get();
			lnsJsonNode.put("cstruct", strCStruct);
			log.info("MAPPER.cstruct >>>>> lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
		
		MultiValueMap<String,String> headers = null;
		if (Flag.flag) {
			headers = new LinkedMultiValueMap<>();
			headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		}
		
		if (Flag.flag) log.info("========================== END: mapper =========================");
		
		return new ResponseEntity<>(lnsJsonNode.toPrettyString(), headers, HttpStatus.OK);
	}
}
