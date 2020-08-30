package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Phone {

	@JsonProperty(value = "code")
	private int code      = 82;
	
	@JsonProperty(value = "number")
	//private long number   = 1012345678;
	private long number   = 1012340000;
}
