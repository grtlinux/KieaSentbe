package org.tain.object.getResult.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqGetResultData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "tr_id")
	private String tr_id;
	
	@StreamAnnotation(length = 10)
	@JsonProperty(value = "user_id")
	private String user_id;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "withdrawal_amount")
	private double withdrawal_amount;
	
	@StreamAnnotation(length = 20)
	@JsonProperty(value = "tr_no")
	private String tr_no;
}
