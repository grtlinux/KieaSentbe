package org.tain.controller;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Sample;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/online/rest/test"})
@Slf4j
public class TestRestController {

	/*
	 * url: http://localhost:18083/v1.0/online/rest/test/data1
	 */
	@RequestMapping(value = {"/data1"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> data1(HttpEntity<String> httpEntity) throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		
		Map<?,?> map = Sample.getMap();
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(map, headers, HttpStatus.OK);
	}
	
	/*
	 * url: http://localhost:18083/v1.0/online/rest/test/data2
	 */
	@RequestMapping(value = {"/data2"}, method = {RequestMethod.GET, RequestMethod.POST})
	public Map<?,?> data2() throws Exception {
		log.info("KANG-20200730 >>>>> {} {}", CurrentInfo.get());
		return Sample.getMap();
	}
}
