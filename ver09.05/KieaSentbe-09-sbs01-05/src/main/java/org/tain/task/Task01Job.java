package org.tain.task;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsRecvQueue;
import org.tain.queue.LnsSendQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Task01Job {

	private final String TITLE = "TASK01_JOB ";
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	private LnsRecvQueue lnsRecvQueue = new LnsRecvQueue();
	
	@Async(value = "task01Task")
	public void task01Job(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		
		if (Flag.flag) {
			// single
			for (int i=0; ; i++) {
				if (Flag.flag) Sleep.run(10 * 1000);
				
				String reqStrData = String.format("REQ-[%04d]  Hello, Server!!! %s", i, LocalDateTime.now());
				String reqTypeCode = "0200100";
				String reqLen = String.format("%04d", 7 + reqStrData.length());
				LnsStream reqLnsStream = new LnsStream(reqLen + reqTypeCode + reqStrData);
				
				LnsQueueObject reqLnsQueueObject = LnsQueueObject.builder()
						.lnsStream(reqLnsStream)
						.lnsRecvQueue(this.lnsRecvQueue)
						.build();
				if (Flag.flag) System.out.println(">>>>> REQ: LnsStream: " + JsonPrint.getInstance().toPrettyJson(reqLnsStream));
				this.lnsSendQueue.set(reqLnsQueueObject);
				
				LnsStream resLnsStream = (LnsStream) this.lnsRecvQueue.get();
				if (Flag.flag) System.out.println(">>>>> RES: LnsStream: " + JsonPrint.getInstance().toPrettyJson(resLnsStream));
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
