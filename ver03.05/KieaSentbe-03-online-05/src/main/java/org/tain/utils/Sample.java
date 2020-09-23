package org.tain.utils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Sample {

	public static Map<?,?> getMap() {
		Map<String,Object> map = new HashMap<>();
		map.put("name", "Kiea 강석");
		map.put("date", LocalDateTime.now());
		map.put("message", "Hello, world!!!");
		return map;
	}
}
