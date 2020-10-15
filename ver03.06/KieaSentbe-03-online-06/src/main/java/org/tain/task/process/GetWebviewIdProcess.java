package org.tain.task.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonNode;
import org.tain.object.lns.LnsStream;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GetWebviewIdProcess {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public LnsStream process(LnsStream reqLnsStream) throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		if (Flag.flag) {
			// 2. Stream to Json
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", reqLnsStream.getTypeCode());
			lnsJsonNode.put("stream", reqLnsStream.getData());
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.s2j {} = \n{}", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("json"));
		}
		
		String strResJson = null;
		if (Flag.flag) {
			// 3. post link
			/*
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJson.setHttpUrl("http://localhost:17082/v0.5/link/getCalculation");
			lnsJson.setHttpMethod("POST");
			lnsJson.setType("200");
			lnsJson = this.lnsHttpClient.post(lnsJson);
			log.info("ONLINE-3 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			*/
			strResJson = "{\n" + 
					"  \"__head_data\" : {\n" + 
					"    \"length\" : \"0306\",\n" + 
					"    \"reqres\" : \"0710\",\n" + 
					"    \"type\" : \"400\",\n" + 
					"    \"trNo\" : \"999999\",\n" + 
					"    \"reqDate\" : \"20201015\",\n" + 
					"    \"reqTime\" : \"100116\",\n" + 
					"    \"resTime\" : \"100116\",\n" + 
					"    \"resCode\" : \"\",\n" + 
					"    \"resMessage\" : \"\",\n" + 
					"    \"reserved\" : \"\"\n" + 
					"  },\n" + 
					"  \"__body_data\" : {\n" + 
					"    \"user_id\" : \"82150\",\n" + 
					"    \"webview_id\" : \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiwidXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI\"\n" + 
					"  }\n" + 
					"}";
		}
		
		String strStream = null;
		if (Flag.flag) {
			// 4. mapper TestRes Json to Stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0710400");
			lnsJsonNode.put("json", strResJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.j2s {} = \n[{}]", lnsJsonNode.getValue("reqResType"), lnsJsonNode.getValue("stream"));
			
			strStream = lnsJsonNode.getValue("stream");
		}
		
		LnsStream resLnsStream = null;
		if (Flag.flag) {
			// 5. lnsStream
			resLnsStream = new LnsStream(strStream);
			log.info("ONLINE-5 >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
		}
		
		return resLnsStream;
	}
}
