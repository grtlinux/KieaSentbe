package org.tain.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "stb-env.json")
@Data
public class LnsEnvJsonProperties {

	private String material;
	private Map<String,String> file;
}
