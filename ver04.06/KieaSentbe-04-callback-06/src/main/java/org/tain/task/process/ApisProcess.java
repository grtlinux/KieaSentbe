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
public class ApisProcess {

	@Autowired
	private LnsHttpClient lnsHttpClient;
	
	public LnsStream process(LnsStream reqLnsStream) throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		String strReqJson = null;
		if (Flag.flag) {
			// 1. Stream to Json
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/s2j");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", reqLnsStream.getTypeCode());
			lnsJsonNode.put("stream", reqLnsStream.getData());
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-1 >>>>> lnsJsonNode.s2j = {}", lnsJsonNode.toPrettyString());
			
			strReqJson = lnsJsonNode.getValue("json");
		}
		
		String strResJson = null;
		String reqResType = null;
		if (Flag.flag) {
			// 2. link
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/link/process");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", reqLnsStream.getTypeCode());
			lnsJsonNode.put("reqJson", strReqJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-2 >>>>> lnsJsonNode.link = {}", lnsJsonNode.toPrettyString());
			
			strResJson = lnsJsonNode.getValue("resJson");
			reqResType = lnsJsonNode.getValue("reqResType");
		}
		
		String strResStream = null;
		if (Flag.flag) {
			// 3. Json to Stream
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("httpUrl", "http://localhost:17087/v0.6/mapper/j2s");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", reqResType);
			lnsJsonNode.put("json", strResJson);
			lnsJsonNode = this.lnsHttpClient.post(lnsJsonNode);
			log.info("ONLINE-3 >>>>> lnsJsonNode.j2s = {}", lnsJsonNode.toPrettyString());
			
			// 4. get stream
			strResStream = lnsJsonNode.getValue("stream");
			log.info("ONLINE-4 >>>>> lnsJsonNode.ResStream = [{}]", strResStream);
		}
		
		LnsStream resLnsStream = null;
		if (Flag.flag) {
			// 5. lnsStream
			resLnsStream = new LnsStream(strResStream);
			log.info("ONLINE-5 >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
		}
		
		return resLnsStream;
	}
}
