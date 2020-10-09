package org.tain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.working.generic.Generic01Working;
import org.tain.working.json.Json01Working;
import org.tain.working.json.Json02Working;
import org.tain.working.json.Json03Working;
import org.tain.working.json.Json04Working;
import org.tain.working.json.Json05Working;
import org.tain.working.json.Json06Working;
import org.tain.working.properties.PropertiesWorking;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaSentbe00JsonTools04Application implements CommandLineRunner {

	public static void main(String[] args) {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		SpringApplication.run(KieaSentbe00JsonTools04Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) job01();  // properties
		if (Flag.flag) job02();  // json
		if (!Flag.flag) job03();  // generic
		if (Flag.flag) job04();
		if (Flag.flag) job05();
		if (Flag.flag) job06();
		if (Flag.flag) job07();
		if (Flag.flag) job08();
		if (Flag.flag) job09();
		if (Flag.flag) job10();
		
		if (Flag.flag) System.exit(0);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private PropertiesWorking propertiesWorking;
	
	private void job01() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			this.propertiesWorking.print();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Json01Working json01Working;
	
	@Autowired
	private Json02Working json02Working;
	
	@Autowired
	private Json03Working json03Working;
	
	@Autowired
	private Json04Working json04Working;
	
	@Autowired
	private Json05Working json05Working;
	
	@Autowired
	private Json06Working json06Working;
	
	private void job02() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (!Flag.flag) {
			if (!Flag.flag) this.json01Working.test01();
			if (!Flag.flag) this.json01Working.test02();
			if (Flag.flag) this.json01Working.test03();
			if (Flag.flag) this.json01Working.test04();
		}
		
		if (!Flag.flag) {
			if (Flag.flag) this.json02Working.test01();
		}
		
		if (!Flag.flag) {
			if (!Flag.flag) this.json03Working.test01();
			if (!Flag.flag) this.json03Working.test02();
			if (Flag.flag) this.json03Working.test03();
		}
		
		if (!Flag.flag) {
			if (Flag.flag) this.json04Working.test01();
		}
		
		if (!Flag.flag) {
			if (Flag.flag) this.json05Working.test01();
		}
		
		if (Flag.flag) {
			if (Flag.flag) this.json06Working.test01();  // LnsMstInfo
			if (Flag.flag) this.json06Working.test02();  // LnsCStruct
			if (Flag.flag) this.json06Working.test03();  // LnsJsonToStream
			if (Flag.flag) this.json06Working.test04();  // LnsStreamToJson
			if (Flag.flag) this.json06Working.test05();  // put
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Generic01Working generic01Working;
	
	private void job03() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) this.generic01Working.test01();
			if (Flag.flag) this.generic01Working.test02();
			if (Flag.flag) this.generic01Working.test03();
			if (Flag.flag) this.generic01Working.test04();
			if (Flag.flag) this.generic01Working.test05();
			if (Flag.flag) this.generic01Working.test06();
			if (Flag.flag) this.generic01Working.test07();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job04() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job05() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job06() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job07() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job08() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job09() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private void job10() {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
		}
	}
}
