package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsSocketTicket;
import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsSendQueue;
import org.tain.queue.LnsSocketProcessQueue;
import org.tain.queue.LnsSocketTicketQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientJob {

	private final String TITLE = "CLIENT_JOB ";
	
	@Autowired
	private LnsSocketTicketQueue lnsSocketTicketQueue;
	
	@Autowired
	private LnsSocketProcessQueue lnsSocketProcessQueue;
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "clientTask")
	public void clientJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		LnsSocketTicket lnsSocketTicket = null;
		if (Flag.flag) {
			lnsSocketTicket = this.lnsSocketProcessQueue.get();  // blocking
			log.info(TITLE + ">>>>> lnsSocketTicket: INFO = {}", lnsSocketTicket);
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			try {
				while (true) {
					LnsQueueObject lnsQueueObject = (LnsQueueObject) this.lnsSendQueue.get();
					
					// send
					LnsStream reqLnsStream = lnsQueueObject.getLnsStream();
					lnsSocketTicket.sendStream(reqLnsStream);
					
					// recv
					LnsStream resLnsStream = lnsSocketTicket.recvStream();
					lnsQueueObject.getLnsRecvQueue().set(resLnsStream);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
			} finally {
				lnsSocketTicket.close();
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) this.lnsSocketTicketQueue.set(lnsSocketTicket);
	}
}
