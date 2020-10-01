package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.ticket.LnsInfoTicket;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.InfoTicketReadyQueue;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.queue.SocketTicketUseQueue;
import org.tain.queue._old.LnsQueueObject;
import org.tain.queue._old.LnsSendQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientJob {

	private final String TITLE = "CLIENT_JOB ";
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private InfoTicketReadyQueue infoTicketReadyQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	private LnsSocketTicket lnsSocketTicket = null;
	
	@Async(value = "clientTask")
	public void clientJob(LnsInfoTicket infoTicket) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", infoTicket, CurrentInfo.get());
		
		if (Flag.flag) {
			this.lnsSocketTicket = this.socketTicketUseQueue.get();  // blocking
			log.info(TITLE + ">>>>> clientJob: INFO = {} {}", infoTicket, this.lnsSocketTicket);
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				while (true) {
					// from SendQueue
					LnsQueueObject lnsQueueObject = (LnsQueueObject) this.lnsSendQueue.get();
					
					// send
					reqLnsStream = lnsQueueObject.getLnsStream();
					this.lnsSocketTicket.sendStream(reqLnsStream);
					
					// recv
					resLnsStream = this.lnsSocketTicket.recvStream();
					lnsQueueObject.getLnsRecvQueue().set(resLnsStream);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
			} finally {
				this.lnsSocketTicket.close();
			}
		}
		
		if (Flag.flag) {
			// return the tickets.
			this.socketTicketReadyQueue.set(this.lnsSocketTicket);
			this.infoTicketReadyQueue.set(infoTicket);
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", infoTicket, CurrentInfo.get());
	}
}
