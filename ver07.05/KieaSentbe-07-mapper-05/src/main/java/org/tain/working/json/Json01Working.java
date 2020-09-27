package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.object._json01._Json01Data;
import org.tain.object._json01._test01._Test01Data;
import org.tain.object.createUser.req._ReqCreateUserAgreements;
import org.tain.object.createUser.req._ReqCreateUserData;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.SubString;
import org.tain.utils.TransferStrAndJson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json01Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) System.out.println("----- 1 -----------------------------------");
			
			String strJson = _Json01Data.get01();
			_Test01Data test01Data = new ObjectMapper().readValue(strJson, _Test01Data.class);
			JsonNode testJsonNode = new ObjectMapper().readTree(strJson);
			
			if (Flag.flag) System.out.println("1 >>>>> " + test01Data);
			if (Flag.flag) System.out.println("1 >>>>> " + JsonPrint.getInstance().toPrettyJson(test01Data));
			if (Flag.flag) System.out.println("2 >>>>> " + testJsonNode.toPrettyString());
		}
		
		if (Flag.flag) {
			if (Flag.flag) System.out.println("----- 2 -----------------------------------");
			
			String strJson = _Json01Data.get01();
			_Test01Data test01Data = new ObjectMapper().readValue(strJson, _Test01Data.class);
			if (Flag.flag) System.out.println("1 >>>>> " + test01Data);
			
			JsonNode jsonNodeTest2 = new ObjectMapper().valueToTree(test01Data);
			if (Flag.flag) System.out.println("2 >>>>> " + jsonNodeTest2);
			
			_Test01Data test01Data3 = new ObjectMapper().treeToValue(jsonNodeTest2, _Test01Data.class);
			if (Flag.flag) System.out.println("3 >>>>> " + test01Data3);
			
			_Test01Data test01Data4 = new ObjectMapper().convertValue(jsonNodeTest2, _Test01Data.class);
			if (Flag.flag) System.out.println("4 >>>>> " + test01Data4);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String jsonData = "{\n" + 
					"  \"agreements\": {\n" + 
					"    \"version\": 1,\n" + 
					"    \"list\": [\n" + 
					"      \"SSFCTS\",\n" + 
					"      \"EFTS\",\n" + 
					"      \"PRIVACY\",\n" + 
					"      \"CAUTIONS\",\n" + 
					"      \"CLAIMS\"\n" + 
					"    ]\n" + 
					"  },\n" + 
					"  \"phone\": {\n" + 
					"    \"iso\": \"KR\",\n" + 
					"    \"number\": \"1012340001\"\n" + 
					"  },\n" + 
					"  \"user_name\": {\n" + 
					"    \"first\": \"Jiwon\",\n" + 
					"    \"middle\": \"\",\n" + 
					"    \"last\": \"Tak\"\n" + 
					"  },\n" + 
					"  \"gender\": \"1\",\n" + 
					"  \"account_number\": \"05012345670001\",\n" + 
					"  \"account_holder_name\": \"Jiwon Tak\",\n" + 
					"  \"birth_date\": \"19701225\",\n" + 
					"  \"id_number\": \"KR\",\n" + 
					"  \"nationality_iso\": \"701225-1230001\",\n" + 
					"  \"id_type\": \"1\",\n" + 
					"  \"email\": \"email0001@sentbe.com\",\n" + 
					"  \"often_send_country_iso\": \"PH\",\n" + 
					"  \"occupation\": \"3\",\n" + 
					"  \"funds_source\": \"3\",\n" + 
					"  \"transfer_purpose\": \"3\"\n" + 
					"}";
			
			_ReqCreateUserData reqData = new ObjectMapper().readValue(jsonData, _ReqCreateUserData.class);
			String reqStream = TransferStrAndJson.getStream(reqData);
			if (Flag.flag) System.out.println(">>>>> [" + reqStream + "]");
			
			String strData = "KR 1012340001          Jiwon                                   Tak                   105012345670001      Jiwon Tak                     19701225  701KR                    1email0001@sentbe.com                              PH     3  3  3";
			if (strData.equals(reqStream)) {
				System.out.println(">>>>> SAME");
			} else {
				System.out.println(">>>>> DIFFERENT");
			}
		}
		
		if (Flag.flag) {
			// createUser
			String strData = "KR 1012340001          Jiwon                                   Tak                   105012345670001      Jiwon Tak                     19701225  701KR                    1email0001@sentbe.com                              PH     3  3  3";
			
			TransferStrAndJson.subString = new SubString(strData);
			_ReqCreateUserData reqData = new _ReqCreateUserData();
			reqData = (_ReqCreateUserData) TransferStrAndJson.getObject(reqData);
			if (Flag.flag) System.out.println(">>>>> reqData: " + reqData);
			
			// TODO: to do last, very important...
			reqData.setAgrements(new _ReqCreateUserAgreements());
			if (Flag.flag) System.out.println(">>>>> PrettyJson: " + JsonPrint.getInstance().toPrettyJson(reqData));
			
		}
	}
}
