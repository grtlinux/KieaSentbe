package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json05Working {

	/*
	 * test01()
	 */
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsMstInfo lnsMstInfo = new LnsMstInfo();
			JsonNode bodyDataInfoNode = lnsMstInfo.getBodyDataInfoNode();
			System.out.println(">>>>> \n" + bodyDataInfoNode.toPrettyString());
		}
	}
}
