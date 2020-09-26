package org.tain.object.deleteUser.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResDeleteUserData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "error")
	private String error;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "code")
	private String code;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "message")
	private String message;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "data")
	private String data;
}
