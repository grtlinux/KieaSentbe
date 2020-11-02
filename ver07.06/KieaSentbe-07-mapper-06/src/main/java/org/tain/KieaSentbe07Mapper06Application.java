package org.tain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;
import org.tain.working.infoTest.InfoTest01Working;
import org.tain.working.jsonTest.Json01Working;
import org.tain.working.properties.PropertiesWorking;
import org.tain.working.tasks.MapperReaderTask;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaSentbe07Mapper06Application implements CommandLineRunner {

	public static void main(String[] args) {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		SpringApplication.run(KieaSentbe07Mapper06Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) job01();  // properties
		if (Flag.flag) job02();  // tasks.MapperReaderJob
		if (Flag.flag) job03();  // jsonTest
		if (Flag.flag) job04();  // infoTest
		if (Flag.flag) job05();
		if (Flag.flag) job06();
		if (Flag.flag) job07();
		if (Flag.flag) job08();
		if (Flag.flag) job09();
		if (Flag.flag) job10();
		
		//if (Flag.flag) System.exit(0);
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
	private MapperReaderTask mapperReaderTask;
	
	private void job02() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) this.mapperReaderTask.runMapperReaderJob();
		}
		
		if (Flag.flag) Sleep.run(3 * 1000);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Json01Working json01Working;
	
	//@Autowired
	//private Json02Working json02Working;
	
	private void job03() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) this.json01Working.test01();
			if (Flag.flag) this.json01Working.test02();
			if (Flag.flag) this.json01Working.test03();
			if (Flag.flag) this.json01Working.test04getCalculation();
			if (Flag.flag) this.json01Working.test05createUser();
			if (Flag.flag) this.json01Working.test06checkUser();
			if (Flag.flag) this.json01Working.test07deleteUser();
			if (Flag.flag) this.json01Working.test08getWebviewId();
			if (Flag.flag) this.json01Working.test09getResult();
			if (Flag.flag) this.json01Working.test10getVerification();
			if (Flag.flag) this.json01Working.test11migrationUser();
		}
		
		if (!Flag.flag) {
			/*
			if (!Flag.flag) this.json02Working.test01();
			if (Flag.flag) this.json02Working.test02();
			*/
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private InfoTest01Working infoTest01Working;
	
	private void job04() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) this.infoTest01Working.test01();
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
