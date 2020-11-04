package org.tain.domain.apis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
//@NoArgsConstructor
public class Apis {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 128)
	private String name;
	
	@Column(name = "mapping", length = 128)
	private String mapping;
	
	@Column(name = "http_url", length = 128)
	private String httpUrl;
	
	@Column(name = "http_method", length = 16)
	private String httpMethod;
	
	@Column(name = "type", length = 3)
	private String type;
	
	@Column(name = "req_type", length = 7)
	private String reqType;
	
	@Column(name = "req_json", length = 1024000)
	private String reqJson;
	
	@Column(name = "res_type", length = 7)
	private String resType;
	
	@Column(name = "res_json", length = 1024000)
	private String resJson;
}
