package org.tain.working.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.StringTools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json01Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			/*
			if (Flag.flag) System.out.println("----- 1 -----------------------------------");
			
			String strJson = _Json01Data.get01();
			_Test01Data test01Data = new ObjectMapper().readValue(strJson, _Test01Data.class);
			JsonNode testJsonNode = new ObjectMapper().readTree(strJson);
			
			if (Flag.flag) System.out.println("1 >>>>> " + test01Data);
			if (Flag.flag) System.out.println("1 >>>>> " + JsonPrint.getInstance().toPrettyJson(test01Data));
			if (Flag.flag) System.out.println("2 >>>>> " + testJsonNode.toPrettyString());
			*/
		}
		
		if (Flag.flag) {
			/*
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
			*/
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			/*
			String jsonData = "{\n" + 
					"  \"agreements\": {\n" + 
					"    \"version\": 1,\n" + 
					"    \"list\": [\n" + 
					"      \"SSFCTS\",\n" + 
					"      \"EFTS\",\n" + 
					"      \"PRIVACY\",\n" + 
					"      \"CAUTIONS\",\n" + 
					"      \"CLAIMS\"\n" + 
					"    ]\n" + 
					"  },\n" + 
					"  \"phone\": {\n" + 
					"    \"iso\": \"KR\",\n" + 
					"    \"number\": \"1012340001\"\n" + 
					"  },\n" + 
					"  \"user_name\": {\n" + 
					"    \"first\": \"Jiwon\",\n" + 
					"    \"middle\": \"\",\n" + 
					"    \"last\": \"Tak\"\n" + 
					"  },\n" + 
					"  \"gender\": \"1\",\n" + 
					"  \"account_number\": \"05012345670001\",\n" + 
					"  \"account_holder_name\": \"Jiwon Tak\",\n" + 
					"  \"birth_date\": \"19701225\",\n" + 
					"  \"id_number\": \"KR\",\n" + 
					"  \"nationality_iso\": \"701225-1230001\",\n" + 
					"  \"id_type\": \"1\",\n" + 
					"  \"email\": \"email0001@sentbe.com\",\n" + 
					"  \"often_send_country_iso\": \"PH\",\n" + 
					"  \"occupation\": \"3\",\n" + 
					"  \"funds_source\": \"3\",\n" + 
					"  \"transfer_purpose\": \"3\"\n" + 
					"}";
			
			_ReqCreateUserData reqData = new ObjectMapper().readValue(jsonData, _ReqCreateUserData.class);
			String reqStream = TransferStrAndJson.getStream(reqData);
			if (Flag.flag) System.out.println(">>>>> [" + reqStream + "]");
			
			String strData = "KR 1012340001          Jiwon                                   Tak                   105012345670001      Jiwon Tak                     19701225  701KR                    1email0001@sentbe.com                              PH     3  3  3";
			if (strData.equals(reqStream)) {
				System.out.println(">>>>> SAME");
			} else {
				System.out.println(">>>>> DIFFERENT");
			}
			*/
		}
		
		if (Flag.flag) {
			/*
			// createUser
			String strData = "KR 1012340001          Jiwon                                   Tak                   105012345670001      Jiwon Tak                     19701225  701KR                    1email0001@sentbe.com                              PH     3  3  3";
			
			TransferStrAndJson.subString = new SubString(strData);
			_ReqCreateUserData reqData = new _ReqCreateUserData();
			reqData = (_ReqCreateUserData) TransferStrAndJson.getObject(reqData);
			if (Flag.flag) System.out.println(">>>>> reqData: " + reqData);
			
			// TODO: to do last, very important...
			reqData.setAgrements(new _ReqCreateUserAgreements());
			if (Flag.flag) System.out.println(">>>>> PrettyJson: " + JsonPrint.getInstance().toPrettyJson(reqData));
			*/
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	public void test03() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		String path1 = this.projEnvParamProperties.getHome() + this.projEnvParamProperties.getBase1();
		String path2 = this.projEnvParamProperties.getHome() + this.projEnvParamProperties.getBase2();
		
		JsonNode jsonNode1 = null;
		JsonNode jsonNode2 = null;
		if (Flag.flag) {
			String jsonSample1 = StringTools.stringFromFile(path1 + "/2-1. detail_req.json");
			//jsonNode1 = JsonPrint.getInstance().getObjectMapper().readTree(jsonSample1);
			jsonNode1 = JsonPrint.getInstance().getObjectMapper().readValue(jsonSample1, JsonNode.class);
			String json1 = null;
			json1 = jsonNode1.toString();
			json1 = JsonPrint.getInstance().getObjectMapper().writeValueAsString(jsonNode1 /* or POJO */); 
			json1 = JsonPrint.getInstance().getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode1 /* or POJO */); 
			log.info(">>>>> json1: {}", json1);
			
			String jsonSample2 = StringTools.stringFromFile(path2 + "/4. getCalculation_req.json");
			//jsonNode2 = JsonPrint.getInstance().getObjectMapper().readTree(jsonSample2);
			jsonNode2 = JsonPrint.getInstance().getObjectMapper().readValue(jsonSample2, JsonNode.class);
			log.info(">>>>> jsonNode2: {}", jsonNode2.toPrettyString());
		}
		
		if (Flag.flag) {
			log.info("------------------ get: asText ---------------------------");
			
			log.info(">>>>> 1. sourceCountry : {}", jsonNode1.get("sourceCountry").asText());
			log.info(">>>>> 1. deliveryMethod : {}", jsonNode1.get("deliveryMethod").asText());
			try {
				log.info(">>>>> 1. dummy : {}", jsonNode1.get("dummy").asText());
			} catch (Exception e) {
				log.error(">>>>> ERROR: {}", e.getMessage());
			}
			
			log.info(">>>>> 2. input_amount : {}", jsonNode2.get("input_amount").asInt());
			log.info(">>>>> 2. input_currency : {}", jsonNode2.get("input_currency").asText());
			log.info(">>>>> 2. exchange_rate_id : {}", jsonNode2.get("exchange_rate_id").asLong());
		}
		
		if (Flag.flag) {
			log.info("------------------ put ---------------------------");
			((ObjectNode) jsonNode1).put("deliveryMethod", "account_deposit");
			log.info(">>>>> 1. deliveryMethod : {}", jsonNode1.get("deliveryMethod").asText());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	public void test04() throws IOException {
		JsonNode jsonNode = null;
		
		String jsonData = "{\n" + 
				"  \"agreements\": {\n" + 
				"    \"version\": 1,\n" + 
				"    \"list\": [\n" + 
				"      \"SSFCTS\",\n" + 
				"      \"EFTS\",\n" + 
				"      \"PRIVACY\",\n" + 
				"      \"CAUTIONS\",\n" + 
				"      \"CLAIMS\"\n" + 
				"    ]\n" + 
				"  },\n" + 
				"  \"phone\": {\n" + 
				"    \"iso\": \"KR\",\n" + 
				"    \"number\": \"1012340001\"\n" + 
				"  },\n" + 
				"  \"user_name\": {\n" + 
				"    \"first\": \"Jiwon\",\n" + 
				"    \"middle\": \"\",\n" + 
				"    \"last\": \"Tak\"\n" + 
				"  },\n" + 
				"  \"gender\": \"1\",\n" + 
				"  \"account_number\": \"05012345670001\",\n" + 
				"  \"account_holder_name\": \"Jiwon Tak\",\n" + 
				"  \"birth_date\": \"19701225\",\n" + 
				"  \"id_number\": \"KR\",\n" + 
				"  \"nationality_iso\": \"701225-1230001\",\n" + 
				"  \"id_type\": \"1\",\n" + 
				"  \"email\": \"email0001@sentbe.com\",\n" + 
				"  \"often_send_country_iso\": \"PH\",\n" + 
				"  \"occupation\": \"3\",\n" + 
				"  \"funds_source\": \"3\",\n" + 
				"  \"transfer_purpose\": \"3\"\n" + 
				"}";
		jsonNode = JsonPrint.getInstance().getObjectMapper().readTree(jsonData);
		//System.out.println("jsonData: " + jsonNode.toPrettyString());
		System.out.println("jsonData: " + JsonPrint.getInstance().toPrettyJson(jsonNode));
		
		String inputJson = "{\"name\":\"Jake\",\"salary\":3000,\"phones\":"
				+ "[{\"phoneType\":\"cell\",\"phoneNumber\":\"111-111-111\"},"
				+ "{\"phoneType\":\"work\",\"phoneNumber\":\"222-222-222\"}],"
				+"\"taskIds\":[11,22,33],"
				+ "\"address\":{\"street\":\"101 Blue Dr\",\"city\":\"White Smoke\"}}";
		jsonNode = JsonPrint.getInstance().getObjectMapper().readTree(inputJson);
		//System.out.println("inputJson: " + jsonNode.toPrettyString());
		System.out.println("inputJson: " + JsonPrint.getInstance().toPrettyJson(jsonNode));

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = null;
		rootNode = objectMapper.readTree(jsonData);
		rootNode = objectMapper.readTree(inputJson);
		System.out.printf("root: %s type=%s%n", rootNode, rootNode.getNodeType());
		traverse(rootNode, 1);
	}

	private void traverse(JsonNode node, int level) {
		if (node.getNodeType() == JsonNodeType.ARRAY) {
			traverseArray(node, level);
		} else if (node.getNodeType() == JsonNodeType.OBJECT) {
			traverseObject(node, level);
		} else {
			throw new RuntimeException("Not yet implemented");
		}
	}

	private void traverseObject(JsonNode node, int level) {
		node.fieldNames().forEachRemaining((String fieldName) -> {
			JsonNode childNode = node.get(fieldName);
			printNode(childNode, fieldName, level);
			//for nested object or arrays
			if (traversable(childNode)) {
				traverse(childNode, level + 1);
			}
		});
	}

	private void traverseArray(JsonNode node, int level) {
		for (JsonNode jsonArrayNode : node) {
			printNode(jsonArrayNode, "arrayElement", level);
			if (traversable(jsonArrayNode)) {
				traverse(jsonArrayNode, level + 1);
			}
		}
	}

	private boolean traversable(JsonNode node) {
		return node.getNodeType() == JsonNodeType.OBJECT ||
				node.getNodeType() == JsonNodeType.ARRAY;
	}

	private void printNode(JsonNode node, String keyName, int level) {
		if (traversable(node)) {
			//System.out.printf("%" + (level * 4 - 3) + "s|-- %s=%s type=%s%n",
			System.out.printf("%" + (level * 4 - 3) + "s+-- %s=%s type=%s%n",
					"", keyName, node.toString(), node.getNodeType());
		} else {
			Object value = null;
			if (node.isTextual()) {
				value = node.textValue();
			} else if (node.isNumber()) {
				value = node.numberValue();
			}//todo add more types
			//System.out.printf("%" + (level * 4 - 3) + "s|-- %s=%s type=%s%n",
			System.out.printf("%" + (level * 4 - 3) + "s+-- %s=%s type=%s%n",
					"", keyName, value, node.getNodeType());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
}

//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////

/*

public class ObjectMapper  extends ObjectCodec  implements Versioned,  java.io.Serializable{
	........
	public JsonNode readTree(InputStream in) throws IOException{....}
	public JsonNode readTree(Reader r) throws IOException {....}
	public JsonNode readTree(String content) throws IOException {....}
	public JsonNode readTree(byte[] content) throws IOException {....}
	public JsonNode readTree(File file)throws IOException, JsonProcessingException{....}
	public JsonNode readTree(URL source) throws IOException {....}
	........
}

//////////////////////////////////////////////////////////////////////////////////

public abstract class JsonNode extends JsonSerializable.Base 
						implements TreeNode, Iterable<JsonNode>{
	
	public abstract <T extends JsonNode> T deepCopy();

	public int size() { .... }

	// Accessing value of the specified element of an array node
	public abstract JsonNode get(int index);
	//Method for accessing value of the specified field of an object node
	public JsonNode get(String fieldName) { ..... }
	//similar to get(....), except that instead of returning null,  a "missing node"  will be returned.
	public abstract JsonNode path(String fieldName);
	
	public Iterator<String> fieldNames(){...}

	//Method for locating node specified by given JSON pointer instances
	public final JsonNode at(JsonPointer ptr){....}

	//Convenience method that is functionally equivalent to:
	//  return at(JsonPointer.valueOf(jsonPointerExpression))
	public final JsonNode at(String jsonPtrExpr) {....}

	protected abstract JsonNode _at(JsonPointer ptr);

	//Return the type of this node
	public abstract JsonNodeType getNodeType();
	
	public final boolean isValueNode() {....}
	public final boolean isContainerNode() {....}
	public boolean isMissingNode() {....}
	public boolean isArray() {.....}
	public boolean isObject() {....}
	public final boolean isPojo() {....}
	public final boolean isNumber() {...}
	public boolean isIntegralNumber() { ....}
	public boolean isFloatingPointNumber() { ..... }
	public boolean isShort() { .... }
	public boolean isInt() { .... }
	public boolean isLong() { .... }
	public boolean isFloat() { .... }
	public boolean isDouble() { .... }
	public boolean isBigDecimal() { .... }
	public boolean isBigInteger() { .... }
	public final boolean isTextual() { .... }
	public final boolean isBoolean() { .... }
	public final boolean isNull() { .... }
	public final boolean isBinary() { .... }

	//conversion
	public boolean canConvertToInt() { .... }
	public boolean canConvertToLong() { .... }
	public String textValue() { .... }
	public byte[] binaryValue() { .... }
	public boolean booleanValue() { .... }
	public Number numberValue() { .... }
	public short shortValue() { .... }
	public int intValue() { .... }
	public long longValue() { .... }
	public float floatValue() { .... }
	public double doubleValue() { .... }
	public BigDecimal decimalValue() { .... }
	public BigInteger bigIntegerValue() { .... }
	
	public abstract String asText();
	public String asText(String defaultValue) {...}
	public int asInt() {...}
	public int asInt(int defaultValue) {....}
	public long asLong() {.....}
	public long asLong(long defaultValue) {....}
	public double asDouble() {.....}
	public double asDouble(double defaultValue) {....}
	public boolean asBoolean() {.....}
	public boolean asBoolean(boolean defaultValue) {....}
	public boolean has(String fieldName) {.....}
	public boolean has(int index) {.....}
	public boolean hasNonNull(String fieldName) {....}
	public boolean hasNonNull(int index) {.....}

	public final Iterator<JsonNode> iterator() { ....}
	public Iterator<JsonNode> elements() {....}
	public Iterator<Map.Entry<String, JsonNode>> fields() {...}
	
	public abstract JsonNode findValue(String fieldName);
	public final List<JsonNode> findValues(String fieldName){....}
	public final List<String> findValuesAsText(String fieldName){...}
	
	public abstract JsonNode findPath(String fieldName);
	public abstract JsonNode findParent(String fieldName);
	
	public final List<JsonNode> findParents(String fieldName){....}

	public abstract List<JsonNode> findValues(String fieldName, List<JsonNode> foundSoFar);
	public abstract List<String> findValuesAsText(String fieldName, List<String> foundSoFar);
	public abstract List<JsonNode> findParents(String fieldName, List<JsonNode> foundSoFar);

	// Method for Object nodes, to access a property that has Object value; 
	//or if no such property exists, to create, add and return such Object node.
	public JsonNode with(String propertyName) {....}
	public JsonNode withArray(String propertyName) {....}
}

*/
