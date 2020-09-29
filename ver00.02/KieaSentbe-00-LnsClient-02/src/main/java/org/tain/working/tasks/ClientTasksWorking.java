package org.tain.working.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.task.ClientJob;
import org.tain.task.ClientMainJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientTasksWorking {

	@Autowired
	private ClientMainJob clientMainJob;
	
	public void runningServerMainTask() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			this.clientMainJob.clientMainJob("CLIENT-MAIN-TASK");
			Sleep.run(1 * 1000);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ClientJob clientJob;
	
	public void runningServerTask() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			int index = 0;
			
			while (true) {
				for (int i=0; i < 1; i++) {
					try {
						this.clientJob.clientJob("CLIENT-TASK-" + index);
						index ++;
						Sleep.run(1 * 1000);
					} catch (Exception e) {
						//System.out.println(">>>>> EXCEPTION: " + e.getMessage());
						Sleep.run(1 * 1000);
					}
				}
				if (Flag.flag) break;
				
				//this.wakeClientTaskQueue.get();
			}
		}
	}
}
