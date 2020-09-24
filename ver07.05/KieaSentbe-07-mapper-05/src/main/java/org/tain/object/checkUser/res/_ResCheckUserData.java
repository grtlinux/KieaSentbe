package org.tain.object.checkUser.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResCheckUserData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "id_number")
	private String id_number;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "sign_status")
	private String sign_status;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "account_number")
	private String account_number;
}
