package org.tain.task.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsJson;
import org.tain.object.lns.LnsStream;
import org.tain.properties.ProjEnvUrlProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class _RefundProcess {

	@SuppressWarnings("unused")
	@Autowired
	private ProjEnvUrlProperties projEnvUrlProperties;
	
	///////////////////////////////////////////////////////////////////////////
	
	public LnsStream process(LnsStream reqLnsStream) throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			// 1. LnsJson
			lnsJson = LnsJson.builder().name("Refund").build();
			lnsJson.setReqStrData(reqLnsStream.getContent());
			log.info("ONLINE-1 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) {
			// 2. mapper TestReq Stream to Json
			lnsJson.setHttpUrl("http://localhost:18086/v1.0/mapper/refund/req/s2j");
			lnsJson.setHttpMethod("POST");
			lnsJson = LnsHttpClient.post(lnsJson);
			log.info("ONLINE-2 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) {
			// 3. post link
			//lnsJson = httpLinkPost(lnsJson);
			lnsJson.setHttpUrl("http://localhost:18082/v1.0/link/refund");
			lnsJson.setHttpMethod("POST");
			lnsJson = LnsHttpClient.post(lnsJson);
			log.info("ONLINE-3 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		if (Flag.flag) {
			// 4. mapper TestRes Json to Stream
			lnsJson.setHttpUrl("http://localhost:18086/v1.0/mapper/refund/res/j2s");
			lnsJson.setHttpMethod("POST");
			lnsJson = LnsHttpClient.post(lnsJson);
			//lnsJson.setResStrData("Hello, world!!! Refund");
			log.info("ONLINE-4 >>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		LnsStream resLnsStream = null;
		if (Flag.flag) {
			// 5. lnsStream
			String resStrData = lnsJson.getResStrData();
			String resTypeCode = "0210600";
			String resLen = String.format("%04d", 7 + resStrData.length());
			resLnsStream = new LnsStream(resLen + resTypeCode + resStrData);
			log.info("ONLINE-5 >>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
		}
		
		return resLnsStream;
	}
}
