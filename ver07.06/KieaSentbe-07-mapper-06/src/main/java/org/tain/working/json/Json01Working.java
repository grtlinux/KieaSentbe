package org.tain.working.json;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonNode;
import org.tain.mapper.LnsJsonToStream;
import org.tain.mapper.LnsMstInfo;
import org.tain.mapper.LnsNodeTools;
import org.tain.mapper.LnsStreamToJson;
import org.tain.properties.ProjEnvParamProperties;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.StringTools;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Json01Working {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			LnsJsonNode lnsJsonNode = new LnsJsonNode();
			lnsJsonNode.put("field-1", "Hello, world");
			lnsJsonNode.put("field-2", 123456);
			lnsJsonNode.put("field-3", 1230000000000000.456);
			lnsJsonNode.put("field-4", true);
			lnsJsonNode.put("field-5", 123450000000000L);
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			lnsJsonNode.put("field-4", "My name is Kiea.");
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			lnsJsonNode.put("jsonNode-1", new LnsJsonNode("{\"name\": \"hello\", \"age\": 20}").get());
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			lnsJsonNode.put("arrayNode-1", new LnsJsonNode("[11, 22, 33, 44]").get());
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			lnsJsonNode.put("arrayNode-2", new LnsJsonNode("[\"hello\", \"world\", \"kiea\"]").get());
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			LnsJsonNode lnsArrNode = new LnsJsonNode("[]");
			lnsArrNode.put("12345");
			lnsArrNode.put(12345);
			lnsArrNode.put(true);
			lnsJsonNode.put("arrayNode-3", lnsArrNode.get());
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
			
			String strJsonData = lnsJsonNode.toPrettyString();
			LnsJsonNode lnsJsonNode2 = new LnsJsonNode(strJsonData);
			if (Flag.flag) System.out.println(">>>>> 2.toPrettyString: " + lnsJsonNode2.toPrettyString());
		}
		
		if (Flag.flag) {
			String strJsonData = "{\n" + 
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
			LnsJsonNode lnsJsonNode = new LnsJsonNode(strJsonData);
			if (Flag.flag) System.out.println(">>>>> fieldNames: " + lnsJsonNode.fieldNames());
			if (Flag.flag) System.out.println(">>>>> toPrettyString: " + lnsJsonNode.toPrettyString());
		}
	}
	
	public void test02() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String strJsonData = "{\n" + 
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
			
			@SuppressWarnings("unused")
			LnsJsonNode lnsJsonNode = new LnsJsonNode(strJsonData);
		}
	}
	
	public void test03() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ProjEnvParamProperties projEnvParamProperties;
	
	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	public void test04getCalculation() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "04_getCalculation_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "04_getCalculation_res.json";
			processTest(fileName);
		}
	}
	
	public void test05createUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "05_createUser_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "05_createUser_res.json";
			processTest(fileName);
		}
	}
	
	public void test06checkUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "06_checkUser_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "06_checkUser_res.json";
			processTest(fileName);
		}
	}
	
	public void test07deleteUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "07_deleteUser_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "07_deleteUser_res.json";
			processTest(fileName);
		}
	}
	
	public void test08getWebviewId() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "08_getWebviewId_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "08_getWebviewId_res.json";
			processTest(fileName);
		}
	}
	
	public void test09getResult() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "09_getResult_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "09_getResult_res.json";
			processTest(fileName);
		}
	}
	
	public void test10getVerification() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "10_getVerification_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "10_getVerification_res.json";
			processTest(fileName);
		}
	}
	
	public void test11migrationUser() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String fileName = "11_migrationUser_req.json";
			processTest(fileName);
		}
		
		if (Flag.flag) {
			String fileName = "11_migrationUser_res.json";
			processTest(fileName);
		}
	}
	
	private void processTest(String fileName) throws Exception {
		log.info("KANG-20200721 >>>>> {} {} {}", fileName, CurrentInfo.get(), "########################################");
		
		if (Flag.flag) {
			// get LnsJsonNode
			String home = this.projEnvParamProperties.getHome();
			String base = this.projEnvParamProperties.getBase();
			String basePath = home + base.replace("jsonInfo", "jsonData");
			//if (Flag.flag) log.info(">>>>>> basePath: " + basePath);
			
			String filePath = basePath + File.separator + fileName;
			//if (Flag.flag) log.info(">>>>>> filePath: " + filePath);
			
			String strJson = StringTools.stringFromFile(filePath);
			//if (Flag.flag) log.info(">>>>>> strJson: " + strJson);
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get(fileName);
			if (Flag.flag) log.info(">>>>>> infoNode: " + lnsMstInfo.getInfoNode().toPrettyString());
			String strJsonHead = lnsMstInfo.getJsonHead();
			//if (Flag.flag) log.info(">>>>>> strJsonHead: " + strJsonHead);
			
			// get LnsJsonNode
			LnsJsonNode nodeData = new LnsJsonNode();
			LnsJsonNode jsonHead = new LnsJsonNode(strJsonHead);
			jsonHead.put("resTime", LnsNodeTools.getTime());
			LnsJsonNode jsonBody = new LnsJsonNode(strJson);
			nodeData.put("__head_data", jsonHead.get());
			nodeData.put("__body_data", jsonBody.get());
			if (Flag.flag) log.info(">>>>>> jsonData: " + nodeData.toPrettyString());
			
			// json to stream
			lnsMstInfo = this.mapperReaderJob.get(fileName);
			String strData = new LnsJsonToStream(lnsMstInfo, nodeData.get()).get();
			if (Flag.flag) log.info(">>>>>> json to stream:\n[{}]", strData);
			
			// stream to json
			LnsJsonNode nodeData2 = new LnsJsonNode(new LnsStreamToJson(lnsMstInfo, strData).get());
			if (Flag.flag) log.info(">>>>>> stream to json: {}", nodeData2.toPrettyString());
		}
	}
}
