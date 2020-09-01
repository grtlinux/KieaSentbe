package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Agreements {

	@JsonProperty(value = "version")
	private int version        = 1;  // 동의 약관 version
	
	@JsonProperty(value = "list")
	private String[] list    = {
			"SSFCTS",   // 소액해외송금약관
			"EFTS",     // 전자금융거래이용약관
			"PRIVACY",  // [옵션] 개인정보처리방침 약관
			"CAUTIONS", // 고유식별정보 약관
			"CLAIMS"    // 개인정보 제3자 제공
	};
}
