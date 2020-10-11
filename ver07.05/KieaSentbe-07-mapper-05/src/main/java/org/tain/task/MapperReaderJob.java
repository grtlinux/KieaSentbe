package org.tain.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MapperReaderJob {

	private String param;
	
	@Async(value = "async_mapperReaderJob")
	public void mapperReaderJob(String param) throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		this.param = param;
		
		if (Flag.flag) {
			while (true) {
				System.out.printf(">>>>> param = %s%n", this.param);
				Sleep.run(5 * 1000);
			}
		}
	}
}