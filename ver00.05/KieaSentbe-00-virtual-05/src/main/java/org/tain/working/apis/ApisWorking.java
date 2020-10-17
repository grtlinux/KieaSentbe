package org.tain.working.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.domain.apis.Apis;
import org.tain.mapper.LnsJsonNode;
import org.tain.repository.apis.ApisRepository;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.LnsHttpClient;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ApisWorking {

	@Autowired
	private ApisRepository apisRepository;
	
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		Apis apis = null;
		if (Flag.flag) {
			apis = this.apisRepository.findApidByMapping("apis/getVerification");
			if (Flag.flag) log.info(">>>>> apis.getVerification = {}", JsonPrint.getInstance().toPrettyJson(apis));
		}
		
		if (Flag.flag) {
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("name", "apis/getVerification");
			lnsJsonNode.put("httpUrl", "http://localhost:17082/v0.6/callback/getVerification");
			lnsJsonNode.put("httpMethod", "POST");
			lnsJsonNode.put("reqResType", "0700700");
			lnsJsonNode.put("reqJson", apis.getReqJson());
			if (Flag.flag) log.info(">>>>> BEFORE: lnsJsonNode = {}", lnsJsonNode.toPrettyString());
			
			lnsJsonNode = LnsHttpClient.post(lnsJsonNode);
			
			lnsJsonNode.put("reqResType", "0710700");
			if (Flag.flag) log.info(">>>>> AFTER: lnsJsonNode = {}", lnsJsonNode.toPrettyString());
		}
	}
}
