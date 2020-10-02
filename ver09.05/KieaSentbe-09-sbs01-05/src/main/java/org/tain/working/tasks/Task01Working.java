package org.tain.working.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.task.Task01Job;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Task01Working {

	private final String TITLE = "TASK01_WORKING ";
	
	@Autowired
	private Task01Job task01Job;
	
	public void runTask01Job() throws Exception {
		log.info(TITLE + ">>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String param = "TASK01-JOB";
			this.task01Job.task01Job(param);
			log.info(TITLE + ">>>>> task01Job = {}", param);
		}
	}
}
