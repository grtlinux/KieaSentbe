package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class UserName {

	@JsonProperty(value = "first")
	private String first       = "Seok";  // 이름

	@JsonProperty(value = "middle")
	private String middle      = "";  // [옵션] 중간이름

	@JsonProperty(value = "last")
	private String last        = "Kang";  // 성
}
