package org.tain.working.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsJson;
import org.tain.object.lns.LnsStream;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Test01Working {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public void test01() throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		String strReqStream = null;
		String strResStream = null;
		//strReqStream = "0138070020000000120201010121212                                                                     "
		//		+ "   1000000KRWKRWPHPPH 20190605175000      ";
		
		strReqStream = "0106070040000000120201010121212                                                                     "
				+ "    123456";
		strResStream = "0306071040000000120201010121212                                                                     "
				+ "    123456"
				+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiw"
				+ "idXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI                                   ";
		
		String strResJson = "{\n" + 
				"  \"__head_data\" : {\n" + 
				"    \"length\" : \"0306\",\n" + 
				"    \"reqres\" : \"0710\",\n" + 
				"    \"type\" : \"400\",\n" + 
				"    \"trNo\" : \"000001\",\n" + 
				"    \"reqDate\" : \"20201010\",\n" + 
				"    \"reqTime\" : \"121212\"\n" + 
				"  },\n" + 
				"  \"__body_data\" : {\n" + 
				"    \"user_id\" : \"123456\",\n" + 
				"    \"webview_id\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiwidXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI\"\n" + 
				"  }\n" + 
				"}";
		
		LnsJson lnsJson = null;
		if (!Flag.flag) {
			/*
			// 1. LnsJson
			lnsJson = LnsJson.builder().name("GetWebviewId").build();
			//lnsJson.setReqStrData(reqLnsStream.getContent());
			lnsJson.setReqStrData(strReqStream);
			log.info("ONLINE-1 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		
		if (!Flag.flag) {
			// 2. mapper TestReq Stream to Json
			lnsJson = LnsJson.builder().name("GetWebviewId").build();
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getWebviewId/req/s2j");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType("400");
			lnsJson.setReqStrData(strReqStream);
			lnsJson = this.lnsHttpClient.post(lnsJson);
			log.info("ONLINE-2 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (!Flag.flag) {
			/*
			// 3. post link
			//lnsJson = httpLinkPost(lnsJson);
			lnsJson.setHttpUrl("http://localhost:17082/v0.5/link/getWebviewId");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType("200");
			lnsJson = this.lnsHttpClient.post(lnsJson);
			log.info("ONLINE-3 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
		}
		
		if (Flag.flag) {
			// 4. mapper TestRes Json to Stream
			lnsJson = LnsJson.builder().name("GetWebviewId").build();
			lnsJson.setHttpUrl("http://localhost:17087/v0.5/mapper/getWebviewId/res/j2s");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType("400");
			lnsJson.setResJsonData(strResJson);
			lnsJson = this.lnsHttpClient.post(lnsJson);
			log.info("ONLINE-4 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		LnsStream resLnsStream = null;
		if (!Flag.flag) {
			// 5. lnsStream
			String resStrData = lnsJson.getResStrData();
			String resTypeCode = "0710200";
			String resLen = String.format("%04d", 7 + resStrData.length());
			resLnsStream = new LnsStream(resLen + resTypeCode + resStrData);
			log.info("ONLINE-5 >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
		}
		
		if (Flag.flag) System.exit(0);
	}
}
