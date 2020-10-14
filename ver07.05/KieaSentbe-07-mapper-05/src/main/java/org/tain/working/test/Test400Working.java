package org.tain.working.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.mapper.LnsJsonToStream;
import org.tain.mapper.LnsMstInfo;
import org.tain.mapper.LnsStreamToJson;
import org.tain.task.MapperReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Test400Working {

	@Autowired
	private MapperReaderJob mapperReaderJob;
	
	public void test0700() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String strReqStream = null;
			strReqStream = "0106070040000000120201010121212                                                                     "
					+ "    123456";
			
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0700400");
			JsonNode node = new LnsStreamToJson(lnsMstInfo, strReqStream).get();
			log.info("MAPPER.req >>>>> node = {}", node.toPrettyString());
			
			String strStream = new LnsJsonToStream(lnsMstInfo, node.toString()).get();
			log.info("MAPPER.req >>>>> strStream    = '{}'", strStream);
			log.info("MAPPER.req >>>>> strReqStream = '{}'", strReqStream);
		}
	}
	
	public void test0710() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String strResStream = null;
			strResStream = "0306071040000000120201010121212                                                                     "
					+ "    123456"
					+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTk4MjU4MjAsImp0aSI6ImJqc2drNjdtb2FxdnBpaTNuazNnIiw"
					+ "idXNlcl9pZCI6ODIxNTB9.Wy2W1nUI5VbgzpMFuDzPLiwgJh0e8XYAcbOHj2XSoHI                                   ";
			LnsMstInfo lnsMstInfo = this.mapperReaderJob.get("0710400");
			JsonNode node = new LnsStreamToJson(lnsMstInfo, strResStream).get();
			log.info("MAPPER.res >>>>> node = {}", node.toPrettyString());
			
			String strStream = new LnsJsonToStream(lnsMstInfo, node.toString()).get();
			log.info("MAPPER.req >>>>> strStream    = '{}'", strStream);
			log.info("MAPPER.req >>>>> strResStream = '{}'", strResStream);
		}
	}
}
