package org.tain.object.migrationUser.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqMigrationUserPhone {

	@StreamAnnotation(length = 3)
	@JsonProperty(value = "iso")
	private String iso;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "number")
	private String number;
}
