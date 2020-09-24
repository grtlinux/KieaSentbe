package org.tain.object.getCalculation.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResGetCalculationData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "exchange_rate_id")
	private String exchange_rate_id;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "from_amount")
	private String from_amount;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "from_currency")
	private String from_currency;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "to_amount")
	private String to_amount;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "to_currency")
	private String to_currency;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "transfer_fee")
	private String transfer_fee;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "base_rate")
	private String base_rate;
}
