package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "stb-env.job")
@Data
public class LnsEnvJobProperties {

	private String accessToken;
	
	private String commitFile;
	private String detailFile;
	private String listFile;
	private String list1File;
	private String validateFile;
}
