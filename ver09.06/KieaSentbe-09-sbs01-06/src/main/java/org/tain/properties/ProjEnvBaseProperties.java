package org.tain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "proj-env.base")
@Data
public class ProjEnvBaseProperties {

	private String name;  // default
	
	private String program;
	private String version;
	private String comment;
	
	/*
	@Value("${proj-env.job.loading.source.path}")
	private String srcPath;
	@Value("${proj-env.job.loading.source.file}")
	private String srcFile;
	@Value("${proj-env.job.loading.target.path}")
	private String tgtPath;
	@Value("${proj-env.job.loading.target.file}")
	private String tgtFile;
	*/
	
	private String dummy;  // null
}
