package org.tain.object.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class User {

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
	
	@JsonProperty(value = "nationality")
	private String nationality         = "KR";
	
	@JsonProperty(value = "id_number")
	private String idNumber            = "7012251234567";
	
	@JsonProperty(value = "email")
	private String email               = "email@sentbe.com";
	
	@JsonProperty(value = "often_send_country")
	private String oftenSendCountry    = "PH";
	
	@JsonProperty(value = "occupation")
	private int occupation             = 3;
	
	@JsonProperty(value = "funds_source")
	private int fundsSource            = 3;
	
	@JsonProperty(value = "transfer_purpose")
	private int transferPurpose        = 3;
	
	private Address address            = new Address();
}
