package org.tain.working.tasks;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsServerJobTicket;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.LnsSocketTicketQueue;
import org.tain.queue.ServerJobQueue;
import org.tain.task.ServerJob;
import org.tain.task.ServerMainJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

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
	private LnsSocketTicketQueue lnsSocketTicketQueue;
	
	public void makeingLnsSocketTicketQueue() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			IntStream.rangeClosed(1, SIZ_SOCKET_TICKET).forEach(index -> {
				LnsSocketTicket ticket = new LnsSocketTicket("TICKET-" + index);
				this.lnsSocketTicketQueue.set(ticket);
			});
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private final int SIZ_SERVER_JOB = 5;
	
	@Autowired
	private ServerJobQueue serverJobQueue;
	
	public void makeingServerJobQueue() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			IntStream.rangeClosed(1, SIZ_SERVER_JOB).forEach(index -> {
				LnsServerJobTicket ticket = new LnsServerJobTicket("SERVER-JOB-" + index);
				this.serverJobQueue.set(ticket);
			});
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ServerMainJob serverMainJob;
	
	public void runningServerMainTask() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String param = "SERVER-MAIN-JOB";
			this.serverMainJob.serverMainJob(param);
			log.info(TITLE + ">>>>> serverMainJob = {}", param);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ServerJob serverJob;
	
	public void runningServerTask() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			int size = this.serverJobQueue.size();
			IntStream.rangeClosed(1, size).forEach(index -> {
				LnsServerJobTicket ticket = this.serverJobQueue.get();
				String param = ticket.getName();
				try {
					this.serverJob.serverJob(param);
					log.info(TITLE + ">>>>> serverJob = {}", param);
				} catch (Exception e) {
					//e.printStackTrace();
					log.error(TITLE + ">>>>> ERROR: " + e.getMessage());
				}
				Sleep.run(1 * 1000);
			});
		}
		
		if (Flag.flag) {
			while (true) {
				LnsServerJobTicket ticket = this.serverJobQueue.get();
				String param = ticket.getName();
				try {
					this.serverJob.serverJob(param);
					log.info(TITLE + ">>>>> serverJob = {}", param);
				} catch (Exception e) {
					//e.printStackTrace();
					log.error(TITLE + ">>>>> ERROR: " + e.getMessage());
				}
				Sleep.run(1 * 1000);
			}
		}
	}
}
