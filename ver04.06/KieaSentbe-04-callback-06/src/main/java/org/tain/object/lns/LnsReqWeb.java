package org.tain.object.lns;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LnsReqWeb {

	private String httpUrl;
	private String httpMethod;
	private String reqJson;
}
