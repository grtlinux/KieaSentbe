package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Phone {

	@JsonProperty(value = "iso")
	private String iso      = "KR";  // [옵션] 전화번호 국제 코드 (ISO)
	
	@JsonProperty(value = "number")
	private String number   = "1033882025";  // 전화번호. unique key
}
