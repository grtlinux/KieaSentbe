package org.tain.object.getCalculation.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqGetCalculationData {

	@StreamAnnotation(length = 50)
	@JsonProperty(value = "input_amount")
	private String input_amount;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "input_currency")
	private String input_currency;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "from_currency")
	private String from_currency;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "to_currency")
	private String to_currency;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "to_country")
	private String to_country;
	
	@StreamAnnotation(length = 50)
	@JsonProperty(value = "exchange_rate_id")
	private String exchange_rate_id;
}
