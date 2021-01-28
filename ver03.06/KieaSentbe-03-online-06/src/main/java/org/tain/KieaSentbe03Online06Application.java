package org.tain;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.working.properties.PropertiesWorking;
import org.tain.working.tasks.ServerTasksWorking;
import org.tain.working.test.Test01Working;
import org.tain.working.test._0700100_CheckUser_Working;
import org.tain.working.test._0700200_GetCalculation_Working;
import org.tain.working.test._0700300_DeleteUser_Working;
import org.tain.working.test._0700400_GetWebviewId_Working;
import org.tain.working.test._0700500_CreateUser_Working;
import org.tain.working.test._0700600_GetResult_Working;
import org.tain.working.test._0700700_GetVerification_Working;
import org.tain.working.test._0700800_MigrationUser_Working;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KieaSentbe03Online06Application implements CommandLineRunner {

	public static void main(String[] args) {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		SpringApplication.run(KieaSentbe03Online06Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) job01();  // properties
		if (Flag.flag) job02();  // server
		if (Flag.flag) job03();  // test
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
	private ServerTasksWorking serverTasksWorking;
	
	private void job02() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			if (Flag.flag) this.serverTasksWorking.makeLnsSocketTicket();
			if (Flag.flag) this.serverTasksWorking.makeLnsInfoTicket();
			if (Flag.flag) this.serverTasksWorking.runFactoryMainJob();
			if (Flag.flag) this.serverTasksWorking.runServerMainJob();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private Test01Working test01Working;
	
	@Autowired
	private _0700100_CheckUser_Working _0700100_CheckUser_Working;
	
	@Autowired
	private _0700200_GetCalculation_Working _0700200_GetCalculation_Working;
	
	@Autowired
	private _0700300_DeleteUser_Working _0700300_DeleteUser_Working;
	
	@Autowired
	private _0700400_GetWebviewId_Working _0700400_GetWebviewId_Working;
	
	@Autowired
	private _0700500_CreateUser_Working _0700500_CreateUser_Working;
	
	@Autowired
	private _0700600_GetResult_Working _0700600_GetResult_Working;
	
	@Autowired
	private _0700700_GetVerification_Working _0700700_GetVerification_Working;
	
	@Autowired
	private _0700800_MigrationUser_Working _0700800_MigrationUser_Working;
	
	private void job03() throws Exception {
		log.info("KANG-20200923 >>>>> {} {}", CurrentInfo.get());
		
		if (!Flag.flag) {
			if (Flag.flag) this.test01Working.test01();
			if (Flag.flag) this._0700100_CheckUser_Working.test01();
			if (Flag.flag) this._0700200_GetCalculation_Working.test01();
			if (Flag.flag) this._0700300_DeleteUser_Working.test01();
			if (Flag.flag) this._0700400_GetWebviewId_Working.test01();
			if (Flag.flag) this._0700500_CreateUser_Working.test01();
			if (Flag.flag) this._0700600_GetResult_Working.test01();
			if (Flag.flag) this._0700700_GetVerification_Working.test01();
			if (Flag.flag) this._0700800_MigrationUser_Working.test01();
		}
		
		if (Flag.flag) {
			if (Flag.flag) this._0700100_CheckUser_Working.test01();
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
