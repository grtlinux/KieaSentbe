package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Phone {

	@JsonProperty(value = "iso")
	private String iso      = "KR";  // [옵션] 전화번호 국제 코드 (ISO)
	
	@JsonProperty(value = "number")
	//private String number   = "1012340000";  // 전화번호
	//private int number   = 1012340000;  // 전화번호
	private int number   = 1012345678;  // 전화번호
	
	/*
	@JsonProperty(value = "code")
	private int code      = 82;
	
	@JsonProperty(value = "number")
	//private long number   = 1012345678;
	private long number   = 1012340000;
	*/
}
