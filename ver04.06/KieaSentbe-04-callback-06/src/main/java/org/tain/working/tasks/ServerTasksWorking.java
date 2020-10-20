package org.tain.working.tasks;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsInfoTicket;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.InfoTicketReadyQueue;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.task.FactoryMainJob;
import org.tain.task.ServerMainJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerTasksWorking {

	private final String TITLE = "SERVER_TASKS_WORKING ";
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private final int SIZ_SOCKET_TICKET = 3;
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	public void makeLnsSocketTicket() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			IntStream.rangeClosed(1, SIZ_SOCKET_TICKET).forEach(index -> {
				LnsSocketTicket ticket = new LnsSocketTicket("SOCKET-TICKET-" + index);
				this.socketTicketReadyQueue.set(ticket);
				log.info(TITLE + ">>>>> makeLnsSocketTicket {}", ticket);
			});
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private final int SIZ_INFO_TICKET = 3;
	
	@Autowired
	private InfoTicketReadyQueue infoTicketReadyQueue;
	
	public void makeLnsInfoTicket() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			IntStream.rangeClosed(1, SIZ_INFO_TICKET).forEach(index -> {
				LnsInfoTicket ticket = new LnsInfoTicket("INFO-TICKET-" + index);
				this.infoTicketReadyQueue.set(ticket);
				log.info(TITLE + ">>>>> makeLnsInfoTicket {}", ticket);
			});
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private FactoryMainJob factoryMainJob;
	
	public void runFactoryMainJob() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String param = "FACTORY-MAIN-JOB";
			this.factoryMainJob.factoryMainJob(param);
			log.info(TITLE + ">>>>> factoryMainJob = {}", param);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ServerMainJob serverMainJob;
	
	public void runServerMainJob() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String param = "SERVER-MAIN-JOB";
			this.serverMainJob.serverMainJob(param);
			log.info(TITLE + ">>>>> serverMainJob = {}", param);
		}
	}
}
