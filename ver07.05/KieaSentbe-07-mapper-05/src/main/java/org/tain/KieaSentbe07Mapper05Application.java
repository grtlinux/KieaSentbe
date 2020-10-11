package org.tain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.working.json.Json01Working;
import org.tain.working.json.Json02Working;
import org.tain.working.properties.PropertiesWorking;
import org.tain.working.tasks.MapperReaderTask;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaSentbe07Mapper05Application implements CommandLineRunner {

	public static void main(String[] args) {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		SpringApplication.run(KieaSentbe07Mapper05Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) job01();  // properties
		if (!Flag.flag) job02();  // tasks: mapperReader
		if (Flag.flag) job03();  // json
		if (Flag.flag) job04();
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
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Json01Working json01Working;
	
	@Autowired
	private Json02Working json02Working;
	
	private void job03() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (!Flag.flag) {
			if (Flag.flag) this.json01Working.test01();
			if (Flag.flag) this.json01Working.test02();
		}
		
		if (Flag.flag) {
			if (Flag.flag) this.json02Working.test01();
			if (Flag.flag) this.json02Working.test02();
		}
		
		//if (Flag.flag) System.exit(0);
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
