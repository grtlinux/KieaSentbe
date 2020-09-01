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
	//private String accountNumber       = "05012345670000";  // 계좌번호
	private String accountNumber       = "05012345678900";  // 계좌번호
	
	@JsonProperty(value = "account_holder_name")
	//private String accountHolderName   = "Jiwon Tak";  // 계좌주명
	private String accountHolderName   = "탁지원";  // 계좌주명
	
	@JsonProperty(value = "birth_date")
	private String birthDate           = "19701225";  // 생년월일(YYYYMMDD)
	
	@JsonProperty(value = "nationality_iso")
	private String nationalityIso      = "KR";  // 국적 (ISO)
	
	@JsonProperty(value = "id_number")
	//private String idNumber            = "701225-1230000";  // 실명번호
	private String idNumber            = "701225-1234567";  // 실명번호
	
	@JsonProperty(value = "email")
	//private String email               = "email0000@sentbe.com";  // [옵션] 이메일
	private String email               = "email@sentbe.com";  // [옵션] 이메일
	
	@JsonProperty(value = "often_send_country_iso")
	private String oftenSendCountryIso = "PH";  // 주송금 국가(ISO)
	
	@JsonProperty(value = "occupation")
	private int occupation             = 3;  // 직업 (규격정의)
	
	@JsonProperty(value = "funds_source")
	private int fundsSource            = 3;  // 자원출처 (규격정의)
	
	@JsonProperty(value = "transfer_purpose")
	private int transferPurpose        = 3;  // 송금목적 (규격정의)
	
	/*
	private Terms terms                = new Terms();
	private Phone phone                = new Phone();
	
	@JsonProperty(value = "first_name")
	private String firstName           = "Jiwon";
	
	@JsonProperty(value = "middle_name")
	private String middleName          = "";
	
	@JsonProperty(value = "last_name")
	private String lastName            = "Tak";
	
	@JsonProperty(value = "gender")
	private int gender                 = 1;
	
	@JsonProperty(value = "account_number")
	private String accountNumber       = "05012345678900";
	
	@JsonProperty(value = "account_holder_name")
	private String accountHolderName   = "Jiwon Tak";
	//private String accountHolderName   = "탁지원";
	
	@JsonProperty(value = "birth_date")
	private String birthDate           = "19701225";
	
	@JsonProperty(value = "nationality_iso")
	//private String nationality         = "KR";
	private String nationality_iso         = "KR";
	
	@JsonProperty(value = "id_number")
	//private String idNumber            = "701225-1234567";
	private String idNumber            = "701225-1234560";
	
	@JsonProperty(value = "email")
	//private String email               = "email@sentbe.com";
	private String email               = "email001@sentbe.com";
	
	@JsonProperty(value = "often_send_country_iso")
	//private String oftenSendCountry    = "PH";
	private String oftenSendCountry_iso    = "PH";
	
	@JsonProperty(value = "occupation")
	private int occupation             = 3;
	
	@JsonProperty(value = "funds_source")
	private int fundsSource            = 3;
	
	@JsonProperty(value = "transfer_purpose")
	private int transferPurpose        = 3;
	
	private Address address            = new Address();
	*/
}
