package org.tain.object.migrationUser.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqMigrationUserData {

	@StreamAnnotation(length = 10)
	@JsonProperty(value = "user_id")
	private int user_id;
	
	@StreamAnnotation
	@JsonProperty(value = "phone")
	private _ReqMigrationUserPhone phone = new _ReqMigrationUserPhone();
	
	@StreamAnnotation
	@JsonProperty(value = "user_name")
	private _ReqMigrationUserName user_name = new _ReqMigrationUserName();
	
	////////////////////////////////////////////////////////
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "gender")
	private int gender;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "account_number")
	private String account_number;
	
	@StreamAnnotation(length = 30)
	@JsonProperty(value = "account_holder_name")
	private String account_holder_name;
	
	@StreamAnnotation(length = 10)
	@JsonProperty(value = "birth_date")
	private String birth_date;
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "nationality_iso")
	private String nationality_iso;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "email")
	private String email;
	
	@StreamAnnotation(length = 5)
	@JsonProperty(value = "often_send_country_iso")
	private String often_send_country_iso;
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "occupation")
	private int occupation;
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "funds_source")
	private int funds_source;
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "transfer_purpose")
	private int transfer_purpose;
}
