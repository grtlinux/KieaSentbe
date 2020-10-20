package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.ticket.LnsInfoTicket;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.InfoTicketReadyQueue;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsSendQueue;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.queue.SocketTicketUseQueue;
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
	
	@SuppressWarnings("unused")
	@Async(value = "clientTask")
	public void clientJob(LnsInfoTicket infoTicket) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", infoTicket, CurrentInfo.get());
		
		LnsSocketTicket lnsSocketTicket = null;
		if (Flag.flag) {
			lnsSocketTicket = this.socketTicketUseQueue.get();  // blocking
			log.info(TITLE + ">>>>> clientJob: INFO = {} {}", infoTicket, lnsSocketTicket);
		}
		
		////////////////////////////////////////////////////
		if (!Flag.flag) {
			/*
			LnsQueueObject lnsQueueObject = null;
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				while (true) {
					// from SendQueue
					lnsQueueObject = (LnsQueueObject) this.lnsSendQueue.get();
					
					// send
					reqLnsStream = lnsQueueObject.getLnsStream();
					lnsSocketTicket.sendStream(reqLnsStream);
					
					// recv
					resLnsStream = lnsSocketTicket.recvStream();
					lnsQueueObject.getLnsRecvQueue().set(resLnsStream);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				// ERROR >>>>> ERROR: return value of read is negative(-)...
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
				if (lnsQueueObject != null)
					this.lnsSendQueue.set(lnsQueueObject);
			} finally {
				lnsSocketTicket.close();
			}
			*/
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			LnsQueueObject lnsQueueObject = null;
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				while (true) {
					// recv
					reqLnsStream = lnsSocketTicket.recvStream();
					//lnsQueueObject.getLnsRecvQueue().set(resLnsStream);
					
					// send
					//reqLnsStream = lnsQueueObject.getLnsStream();
					resLnsStream = new LnsStream("0106071070000000020201020093033                                                                     HW2009201010        ");
					lnsSocketTicket.sendStream(resLnsStream);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				// ERROR >>>>> ERROR: return value of read is negative(-)...
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
				if (lnsQueueObject != null)
					this.lnsSendQueue.set(lnsQueueObject);
			} finally {
				lnsSocketTicket.close();
			}
		}
		
		if (Flag.flag) {
			// return the tickets.
			this.socketTicketReadyQueue.set(lnsSocketTicket);
			this.infoTicketReadyQueue.set(infoTicket);
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", infoTicket, CurrentInfo.get());
	}
}
