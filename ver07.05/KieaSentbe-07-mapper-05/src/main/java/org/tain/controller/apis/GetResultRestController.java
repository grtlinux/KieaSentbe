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
import org.tain.mapper.LnsJsonToStream;
import org.tain.mapper.LnsMstInfo;
import org.tain.mapper.LnsStreamToJson;
import org.tain.object.lns.LnsJson;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = {"/mapper/getResult"})
@Slf4j
public class GetResultRestController {

	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * http://localhost:17087/v0.5/mapper/getResult/req/j2s
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
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			TransferStrAndJson.subString = new SubString(lnsJson.getReqStrData());
			_ReqGetResultData reqData = new _ReqGetResultData();
			reqData = (_ReqGetResultData) TransferStrAndJson.getObject(reqData);
			
			lnsJson.setReqJsonData(JsonPrint.getInstance().toJson(reqData));
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700" + lnsJson.getType());
			JsonNode node = new LnsStreamToJson(lnsMstInfo, lnsJson.getReqStrData()).get();
			lnsJson.setReqJsonData(node.toPrettyString());
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/getResult/req/s2j
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
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			TransferStrAndJson.subString = new SubString(lnsJson.getResStrData());
			_ResGetResultData resData = new _ResGetResultData();
			resData = (_ResGetResultData) TransferStrAndJson.getObject(resData);
			
			lnsJson.setResJsonData(JsonPrint.getInstance().toJson(resData));
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0710" + lnsJson.getType());
			JsonNode node = new LnsStreamToJson(lnsMstInfo, lnsJson.getResStrData()).get();
			lnsJson.setResJsonData(node.toPrettyString());
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
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
	 * http://localhost:17087/v0.5/mapper/getResult/req/j2s
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
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			_ReqGetResultData reqData = new ObjectMapper().readValue(lnsJson.getReqJsonData(), _ReqGetResultData.class);
			String reqStream = TransferStrAndJson.getStream(reqData);
			lnsJson.setReqStrData(reqStream);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700" + lnsJson.getType());
			String strStream = new LnsJsonToStream(lnsMstInfo, lnsJson.getReqJsonData()).get();
			lnsJson.setReqStrData(strStream);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/getResult/res/j2s
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
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			_ResGetResultData resData = new ObjectMapper().readValue(lnsJson.getResJsonData(), _ResGetResultData.class);
			String resStream = TransferStrAndJson.getStream(resData);
			lnsJson.setResStrData(resStream);
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0710" + lnsJson.getType());
			String strStream = new LnsJsonToStream(lnsMstInfo, lnsJson.getResJsonData()).get();
			lnsJson.setResStrData(strStream);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
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
	 * http://localhost:17087/v0.5/mapper/getResult/req/cstruct
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
		if (!Flag.flag) {
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			//_ReqCommitData reqData = new ObjectMapper().readValue(lnsJson.getReqJsonData(), _ReqCommitData.class);
			String reqCStruct = TransferStrAndJson.getCStruct(new _ReqGetResultData());
			lnsJson.setBatData(reqCStruct);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700" + lnsJson.getType());
			String reqCStruct = new LnsCStruct(lnsMstInfo).get();
			lnsJson.setBatData(reqCStruct);
			log.info("MAPPER.req >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
	
	/*
	 * http://localhost:17087/v0.5/mapper/getResult/res/cstruct
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
		if (!Flag.flag) {
			/*
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			//_ResCommitData resData = new ObjectMapper().readValue(lnsJson.getResJsonData(), _ResCommitData.class);
			String resCStruct = TransferStrAndJson.getCStruct(new _ResGetResultData());
			lnsJson.setBatData(resCStruct);
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		if (Flag.flag) {
			lnsJson = new ObjectMapper().readValue(reqHttpEntity.getBody(), LnsJson.class);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0710" + lnsJson.getType());
			String resCStruct = new LnsCStruct(lnsMstInfo).get();
			lnsJson.setBatData(resCStruct);
			log.info("MAPPER.res >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) log.info("========================================================");
		
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(lnsJson, headers, HttpStatus.OK);
	}
}
