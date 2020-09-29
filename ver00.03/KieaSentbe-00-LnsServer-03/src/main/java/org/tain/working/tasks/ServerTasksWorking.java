package org.tain.working.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.queue.WakeServerTaskQueue;
import org.tain.task.ServerMainJob;
import org.tain.task.ServerJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerTasksWorking {

	private final String TITLE = "SERVER_TASKS_WORKING ";
	
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
	
	@Autowired
	private WakeServerTaskQueue wakeServerTaskQueue;
	
	public void runningServerTask() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			int index = 0;
			
			// initialize
			for (; index < 3; index++) {
				String param = "SERVER-JOB-" + index;
				this.serverJob.serverJob(param);
				log.info(TITLE + ">>>>> 1. serverJob = {}", param);
				Sleep.run(1 * 1000);
			}
			
			// other
			while (true) {
				this.wakeServerTaskQueue.get();  // blocking
				
				String param = "SERVER-JOB-" + index;
				this.serverJob.serverJob(param);
				log.info(TITLE + ">>>>> 2. serverJob = {}", param);
				index ++;
				Sleep.run(1 * 1000);
			}
		}
	}
}
