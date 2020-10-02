package org.tain.task.process;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsJson;
import org.tain.object.lns.LnsStream;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckUserProcess {

	private final String TITLE = "CHECK_USER_PROCESS";
	
	public LnsStream process(LnsStream reqLnsStream) throws Exception {
		log.info("KANG-20200908 >>>>> {}", CurrentInfo.get());
		
		LnsJson lnsJson = null;
		if (Flag.flag) {
			// 1. LnsJson
			lnsJson = LnsJson.builder().name("CheckUser").build();
			lnsJson.setReqStrData(reqLnsStream.getContent());
			log.info(TITLE + ">>>>> lnsJson = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
		}
		
		LnsStream resLnsStream = null;
		if (Flag.flag) {
			// 5. lnsStream
			String resStrData = String.format("RES-[0000]  Hello, client!!! %s", LocalDateTime.now());
			String resTypeCode = "0210100";
			String resLen = String.format("%04d", 7 + resStrData.length());
			resLnsStream = new LnsStream(resLen + resTypeCode + resStrData);
			log.info(TITLE + ">>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
		}
		
		return resLnsStream;
	}
}
