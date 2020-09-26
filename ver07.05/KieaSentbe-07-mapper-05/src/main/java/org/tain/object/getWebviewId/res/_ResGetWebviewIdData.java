package org.tain.object.getWebviewId.res;

import org.tain.annotation.StreamAnnotation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
//@JsonIgnoreProperties(value = {"idLong", "idInt"})  // to the below
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class _ResGetWebviewIdData {

	@StreamAnnotation(length = 20)
	@JsonProperty(value = "user_id")
	private String user_id;
	
	@StreamAnnotation(length = 200)
	@JsonProperty(value = "webview_id")
	private String webview_id;
}
