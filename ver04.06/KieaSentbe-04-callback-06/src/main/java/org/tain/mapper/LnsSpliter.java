package org.tain.mapper;

import lombok.Data;

@Data
//@Slf4j
public class LnsSpliter {

	private String pathName;
	private String fieldName;
	
	public LnsSpliter(String name) {
		int idx = name.lastIndexOf('/');
		if (idx < 0) {
			this.pathName = name;
			this.fieldName = name;
		}
	}
	
	public LnsSpliter(String pathName, String fieldName) {
		this.pathName = pathName;
		this.fieldName = fieldName;
	}
}
