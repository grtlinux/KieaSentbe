package org.tain.working;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.properties.LnsEnvBaseProperties;
import org.tain.properties.LnsEnvJobProperties;
import org.tain.properties.LnsEnvJsonProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PropertiesWorking {

	@Autowired
	private LnsEnvBaseProperties lnsEnvBaseProperties;
	
	@Autowired
	private LnsEnvJsonProperties lnsEnvJsonProperties;
	
	@Autowired
	private LnsEnvJobProperties lnsEnvJobProperties;

	public void printProperties() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			JsonPrint.getInstance().printPrettyJson("BASE", this.lnsEnvBaseProperties);
			JsonPrint.getInstance().printPrettyJson("JSON", this.lnsEnvJsonProperties);
			JsonPrint.getInstance().printPrettyJson("JOB", this.lnsEnvJobProperties);
		}
	}
}
