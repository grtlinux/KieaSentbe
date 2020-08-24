package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "stb-env.base")
@Data
public class LnsEnvBaseProperties {

	private String program;
	private String version;
	private String comment;
}
