package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "proj-env.param")
@Data
public class ProjEnvParamProperties {

	private String name;  // default
	
	private String home;
	private String base1;
	private String base2;
	
	private String dummy;  // null
}
