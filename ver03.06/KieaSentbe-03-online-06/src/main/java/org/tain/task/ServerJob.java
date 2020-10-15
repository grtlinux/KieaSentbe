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
import org.tain.task.process.ApisProcess;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerJob {

	private final String TITLE = "SERVER_JOB ";
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private InfoTicketReadyQueue infoTicketReadyQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ApisProcess apiProcess;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "serverTask")
	public void serverJob(LnsInfoTicket infoTicket) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", infoTicket, CurrentInfo.get());
		
		LnsSocketTicket lnsSocketTicket = null;
		if (Flag.flag) {
			lnsSocketTicket = this.socketTicketUseQueue.get();  // blocking
			log.info(TITLE + ">>>>> serverJob: INFO = {} {}", infoTicket, lnsSocketTicket);
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				while (true) {
					// recv
					reqLnsStream = lnsSocketTicket.recvStream();
					if (Flag.flag) log.info(TITLE + ">>>>> reqLnsStream = {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
					
					// process
					resLnsStream = this.apiProcess.process(reqLnsStream);
					
					// send
					lnsSocketTicket.sendStream(resLnsStream);
					if (Flag.flag) log.info(TITLE + ">>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
				}
			} catch (Exception e) {
				//e.printStackTrace();
				// ERROR >>>>> ERROR: return value of read is negative(-)...
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
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
