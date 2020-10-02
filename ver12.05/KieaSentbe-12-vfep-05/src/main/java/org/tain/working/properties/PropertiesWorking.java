package org.tain.working.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.properties.ProjEnvBaseProperties;
import org.tain.properties.ProjEnvJobProperties;
import org.tain.properties.ProjEnvJsonProperties;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.properties.ProjEnvUrlProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PropertiesWorking {

	@Autowired
	private ProjEnvBaseProperties projEnvBaseProperties;
	
	@Autowired
	private ProjEnvJobProperties projEnvJobProperties;
	
	@Autowired
	private ProjEnvJsonProperties projEnvJsonProperties;
	
	@Autowired
	private ProjEnvUrlProperties projEnvUrlProperties;
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	public void print() {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			JsonPrint.getInstance().printPrettyJson("- BASE -- " , this.projEnvBaseProperties);
			JsonPrint.getInstance().printPrettyJson("- JOB --- " , this.projEnvJobProperties);
			JsonPrint.getInstance().printPrettyJson("- JSON -- " , this.projEnvJsonProperties);
			JsonPrint.getInstance().printPrettyJson("- URL --- " , this.projEnvUrlProperties);
			JsonPrint.getInstance().printPrettyJson("- PARAM - " , this.projEnvParamProperties);
		}
	}
}
