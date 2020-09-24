package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "proj-env.job")
@Data
public class ProjEnvJobProperties {

	private String name;  // default
	
	private String onlineHost;
	private int onlinePort;
	
	private String dummy;  // null
}
