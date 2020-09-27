package org.tain.object.createUser.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqCreateUserData {

	@StreamAnnotation
	@JsonProperty(value = "agrements")
	private _ReqCreateUserAgreements agrements = new _ReqCreateUserAgreements();
	
	@StreamAnnotation
	@JsonProperty(value = "phone")
	private _ReqCreateUserPhone phone = new _ReqCreateUserPhone();
	
	@StreamAnnotation
	@JsonProperty(value = "user_name")
	private _ReqCreateUserName user_name = new _ReqCreateUserName();
	
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
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "id_number")
	private String id_number;
	
	@StreamAnnotation(length = 3)
	@JsonProperty(value = "id_type")
	private int id_type;
	
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
