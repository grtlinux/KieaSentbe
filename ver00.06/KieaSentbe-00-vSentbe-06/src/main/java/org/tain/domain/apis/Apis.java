package org.tain.domain.apis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "tb_apis"
, indexes = {
		@Index(name = "apis_idx0", unique = true, columnList = "mapping"),
}
)
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
	
	@Column(name = "req_json", length = 1024000)
	private String reqJson;
	
	@Column(name = "res_json", length = 1024000)
	private String resJson;
}
