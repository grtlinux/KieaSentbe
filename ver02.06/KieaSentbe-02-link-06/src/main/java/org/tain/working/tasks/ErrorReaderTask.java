package org.tain.working.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.task.ErrorReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorReaderTask {

	@Autowired
	private ErrorReaderJob errorReaderJob;
	
	public void runErrorReaderJob() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String param = "ERROR-READER-JOB";
			this.errorReaderJob.errorReaderJob(param);
		}
	}
}
