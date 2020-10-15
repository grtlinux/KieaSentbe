package org.tain.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@Configuration
//@EnableWebMvc
public class WebJsonConfig implements WebMvcConfigurer {

	@Value("${json.browser-print.enabled:true}")
	private boolean jsonBrowserPrintEnabled;
		
	//@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		if (this.jsonBrowserPrintEnabled)
			converters.add(this.mappingJackson2HttpMessageConverter());
	}
	
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
		mappingConverter.setObjectMapper(this.objectMapper());
		return mappingConverter;
	}
	
	private ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.registerModule(new Hibernate5Module());
		return objectMapper;
	}
}