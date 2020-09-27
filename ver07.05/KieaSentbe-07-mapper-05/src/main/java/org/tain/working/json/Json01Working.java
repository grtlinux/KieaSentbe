package org.tain.working.json;

import org.springframework.stereotype.Component;
import org.tain.object._json01._Json01Data;
import org.tain.object._json01._test01._Test01Data;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json01Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) System.out.println("----- 1 -----------------------------------");
			
			String strJson = _Json01Data.get01();
			_Test01Data test01Data = new ObjectMapper().readValue(strJson, _Test01Data.class);
			JsonNode testJsonNode = new ObjectMapper().readTree(strJson);
			
			if (Flag.flag) System.out.println("1 >>>>> " + test01Data);
			if (Flag.flag) System.out.println("1 >>>>> " + JsonPrint.getInstance().toPrettyJson(test01Data));
			if (Flag.flag) System.out.println("2 >>>>> " + testJsonNode.toPrettyString());
		}
		
		if (Flag.flag) {
			if (Flag.flag) System.out.println("----- 2 -----------------------------------");
			
			String strJson = _Json01Data.get01();
			_Test01Data test01Data = new ObjectMapper().readValue(strJson, _Test01Data.class);
			if (Flag.flag) System.out.println("1 >>>>> " + test01Data);
			
			JsonNode jsonNodeTest2 = new ObjectMapper().valueToTree(test01Data);
			if (Flag.flag) System.out.println("2 >>>>> " + jsonNodeTest2);
			
			_Test01Data test01Data3 = new ObjectMapper().treeToValue(jsonNodeTest2, _Test01Data.class);
			if (Flag.flag) System.out.println("3 >>>>> " + test01Data3);
			
			_Test01Data test01Data4 = new ObjectMapper().convertValue(jsonNodeTest2, _Test01Data.class);
			if (Flag.flag) System.out.println("4 >>>>> " + test01Data4);
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			
		}
	}
}
