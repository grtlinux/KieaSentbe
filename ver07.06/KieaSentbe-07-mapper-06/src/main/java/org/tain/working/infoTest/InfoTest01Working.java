package org.tain.working.infoTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsMstInfo;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InfoTest01Working {

	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsJsonNode lnsJsonNode = null;
			if (Flag.flag) {
				lnsJsonNode = new LnsJsonNode();
				lnsJsonNode.put("reqResType", "0700200");
			}
			if (Flag.flag) {
				
				LnsMstInfo lnsMstInfo = this.mapperReaderJob.get(lnsJsonNode.getValue("reqResType"));
				log.info("MAPPER.get = {}", JsonPrint.getInstance().toPrettyJson(lnsMstInfo));
			}
		}
	}
}
