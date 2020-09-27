package org.tain.object.createUser.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResCreateUserData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "user_id")
	private String user_id;
	
	@StreamAnnotation(length = 200)
	@JsonProperty(value = "webview_id")
	private String webview_id;
	
	@StreamAnnotation(length = 5)
	@JsonProperty(value = "verification_id")
	private String verification_id;
}
