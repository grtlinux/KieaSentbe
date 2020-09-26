package org.tain.object.createUser.req;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ReqCreateUserAgreements {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "version")
	private String version;
	
	@JsonProperty(value = "list")
	private String[] list = new String[] { "SSFCTS", "EFTS", "PRIVACY", "CAUTIONS", "CLAIMS" };
	//private String[] list = (String[]) Arrays.asList("SSFCTS", "EFTS", "PRIVACY", "CAUTIONS", "CLAIMS").toArray();
	//private String[] list = Arrays.asList("SSFCTS", "EFTS", "PRIVACY", "CAUTIONS", "CLAIMS").toArray(new String[5]);
	//private List<String> list = new ArrayList<>();
	//private List<String> list = Arrays.asList("SSFCTS", "EFTS", "PRIVACY", "CAUTIONS", "CLAIMS");
	//private List<String> list = Arrays.asList(new String[] {"SSFCTS", "EFTS", "PRIVACY", "CAUTIONS", "CLAIMS"});
	
}
