package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsInfoTicket;
import org.tain.queue.InfoTicketReadyQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FactoryMainJob {

	private final String TITLE = "FACTORY_MAIN_JOB ";
	
	@Autowired
	private InfoTicketReadyQueue infoTicketReadyQueue;
	
	@Autowired
	private ServerJob serverJob;
	
	@Async(value = "factoryMainTask")
	public void factoryMainJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) {
			while (true) {
				LnsInfoTicket ticket = this.infoTicketReadyQueue.get();
				Sleep.run(1 * 1000);
				try {
					this.serverJob.serverJob(ticket);
					log.info(TITLE + ">>>>> serverJob = {}", ticket);
				} catch (Exception e) {
					//e.printStackTrace();
					log.error(TITLE + ">>>>> ERROR: " + e.getMessage());
				}
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
