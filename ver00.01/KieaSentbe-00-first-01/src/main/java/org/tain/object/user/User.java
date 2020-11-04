package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class User {

	/*
	 * unique key
	 *     idNumber(실명번호)
	 *     email
	 *     phone
	 */
	
	@JsonProperty(value = "agreements")
	private Agreements agreements      = new Agreements();
	
	@JsonProperty(value = "phone")
	private Phone phone                = new Phone();
	
	@JsonProperty(value = "user_name")
	private UserName userName          = new UserName();
	
	/////////////////////////////////////////////////////////////////
	
	@JsonProperty(value = "gender")
	private int gender                 = 1;  // 성별   남(1), 여(2)
	
	@JsonProperty(value = "account_number")
	//private String accountNumber       = "05012345670000";  // 계좌번호. unique key
	private String accountNumber       = "01012345678001";  // 계좌번호. unique key
	
	@JsonProperty(value = "account_holder_name")
	//private String accountHolderName   = "Jiwon Tak";  // 계좌주명
	private String accountHolderName   = "Seok Kang";  // 계좌주명
	
	@JsonProperty(value = "birth_date")
	private String birthDate           = "19701225";  // 생년월일(YYYYMMDD)
	
	@JsonProperty(value = "nationality_iso")
	private String nationalityIso      = "KR";  // 국적 (ISO)
	
	@JsonProperty(value = "id_number")
	private String idNumber            = "801225-1230001";  // 실명번호. unique key
	
	@JsonProperty(value = "id_type")
	private int idType                 = 1;     // id 타입
	
	@JsonProperty(value = "email")
	private String email               = "email0001@sentbe.com";  // [옵션] 이메일
	
	@JsonProperty(value = "often_send_country_iso")
	private String oftenSendCountryIso = "PH";  // 주송금 국가(ISO)
	
	@JsonProperty(value = "occupation")
	private int occupation             = 3;  // 직업 (규격정의)
	
	@JsonProperty(value = "funds_source")
	private int fundsSource            = 3;  // 자원출처 (규격정의)
	
	@JsonProperty(value = "transfer_purpose")
	private int transferPurpose        = 3;  // 송금목적 (규격정의)
}
