package org.tain.object.getVerification.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResGetVerificationData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "verification_code")
	private String verification_code;
}
