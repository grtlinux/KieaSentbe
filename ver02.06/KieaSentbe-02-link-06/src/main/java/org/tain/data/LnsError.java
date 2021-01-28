package org.tain.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LnsError {

	private String error_org;
	private String error_server;
	private String error_code;
	private String error_message;
	private String error_msg;
	private String error_regex;
	private String comment;
	
	public String getKey() {
		return this.error_org + "_" + this.error_server + "_" + this.error_code;
	}
}
