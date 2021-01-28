package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "proj-env.param")
@Data
public class ProjEnvParamProperties {

	private String name;  // default
	
	private String sentbeClientKey;
	private String sentbeSecretKeyForData;
	private String sentbeSecretKeyForHmac;
	
	private String home;
	private String base;
	private String infoPath;
	private String dataPath;
	
	private String dummy;  // null
}
