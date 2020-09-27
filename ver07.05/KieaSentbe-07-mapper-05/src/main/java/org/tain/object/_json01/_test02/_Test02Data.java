package org.tain.object._json01._test02;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _Test02Data {

	@StreamAnnotation(length = 50)
	@JsonProperty(value = "field_0201")
	private String field02;
}
