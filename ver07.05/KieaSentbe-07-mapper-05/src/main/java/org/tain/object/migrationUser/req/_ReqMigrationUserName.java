package org.tain.object.migrationUser.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqMigrationUserName {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "first")
	private String first;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "middle")
	private String middle;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "last")
	private String last;
}
