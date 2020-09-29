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

	@Autowired
	private ServerMainJob serverMainJob;
	
	public void runningServerMainTask() {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			try {
				this.serverMainJob.serverMainJob("SERVER-MAIN-TASK");
			} catch (Exception e) {
				//System.out.println(">>>>> EXCEPTION: " + e.getMessage());
				Sleep.run(3 * 1000);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ServerJob serverJob;
	
	@Autowired
	private WakeServerTaskQueue wakeServerTaskQueue;
	
	public void runningServerTask() {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			int index = 0;
			
			while (true) {
				for (int i=0; i < 3; i++) {
					try {
						this.serverJob.serverJob("SERVER-TASK-" + index);
						index ++;
						Sleep.run(3 * 1000);
					} catch (Exception e) {
						log.error("ERROR >>>>> EXCEPTION: " + e.getMessage());
						Sleep.run(1 * 1000);
					}
				}
				this.wakeServerTaskQueue.get();  // blocking
			}
		}
	}
}
