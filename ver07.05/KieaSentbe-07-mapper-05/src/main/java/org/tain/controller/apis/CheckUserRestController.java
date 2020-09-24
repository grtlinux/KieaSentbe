package org.tain.controller.apis;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tain.object.checkUser.req._ReqCheckUserData;
import org.tain.object.checkUser.res._ResCheckUserData;
import org.tain.object.lns.LnsJson;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.SubString;
import org.tain.utils.TransferStrAndJson;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/mapper/checkUser"})
@Slf4j
public class CheckUserRestController {

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/req/j2s
	 */
	@RequestMapping(value = {"/req/s2j"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> commitReqStrToJson(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.req >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.req >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			TransferStrAndJson.subString = new SubString(lnsJson.getReqStrData());
			_ReqCheckUserData reqData = new _ReqCheckUserData();
			reqData = (_ReqCheckUserData) TransferStrAndJson.getObject(reqData);
			
			lnsJson.setReqJsonData(JsonPrint.getInstance().toJson(reqData));
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/req/s2j
	 */
	@RequestMapping(value = {"/res/s2j"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> commitResStrToJson(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.res >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.res >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			TransferStrAndJson.subString = new SubString(lnsJson.getResStrData());
			_ResCheckUserData resData = new _ResCheckUserData();
			resData = (_ResCheckUserData) TransferStrAndJson.getObject(resData);
			
			lnsJson.setResJsonData(JsonPrint.getInstance().toJson(resData));
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/req/j2s
	 */
	@RequestMapping(value = {"/req/j2s"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> commitReqJsonToStr(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.req >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.req >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			_ReqCheckUserData reqData = new ObjectMapper().readValue(lnsJson.getReqJsonData(), _ReqCheckUserData.class);
			String reqStream = TransferStrAndJson.getStream(reqData);
			lnsJson.setReqStrData(reqStream);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/res/j2s
	 */
	@RequestMapping(value = {"/res/j2s"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> commitResJsonToStr(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.res >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.res >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			_ResCheckUserData resData = new ObjectMapper().readValue(lnsJson.getResJsonData(), _ResCheckUserData.class);
			String resStream = TransferStrAndJson.getStream(resData);
			lnsJson.setResStrData(resStream);
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/req/cstruct
	 */
	@RequestMapping(value = {"/req/cstruct"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> detailReqCStruct(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.req >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.req >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			//_ReqCommitData reqData = new ObjectMapper().readValue(lnsJson.getReqJsonData(), _ReqCommitData.class);
			String reqCStruct = TransferStrAndJson.getCStruct(new _ReqCheckUserData());
			lnsJson.setBatData(reqCStruct);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/checkUser/res/cstruct
	 */
	@RequestMapping(value = {"/res/cstruct"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> commitResCStruct(HttpEntity<String> reqHttpEntity) throws Exception {
		log.info("KANG-20200623 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) log.info("========================================================");
		
		if (Flag.flag) {
			log.info("MAPPER.res >>>>> Headers = {}", reqHttpEntity.getHeaders());
			log.info("MAPPER.res >>>>> Body = {}", reqHttpEntity.getBody());
		}
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			//_ResCommitData resData = new ObjectMapper().readValue(lnsJson.getResJsonData(), _ResCommitData.class);
			String resCStruct = TransferStrAndJson.getCStruct(new _ResCheckUserData());
			lnsJson.setBatData(resCStruct);
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
}
