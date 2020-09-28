package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.lns.LnsStreamPacket;
import org.tain.queue.LnsStreamPacketQueue;
import org.tain.task.process.CheckUserProcess;
import org.tain.task.process.CreateUserProcess;
import org.tain.task.process.DeleteUserProcess;
import org.tain.task.process.GetCalculationProcess;
import org.tain.task.process.GetResultProcess;
import org.tain.task.process.GetVerificationProcess;
import org.tain.task.process.GetWebviewIdProcess;
import org.tain.task.process.MigrationUserProcess;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerJob {

	@Autowired
	private LnsStreamPacketQueue lnsStreamPacketQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private CheckUserProcess checkUserProcess;
	
	@Autowired
	private GetCalculationProcess getCalculationProcess;
	
	@Autowired
	private DeleteUserProcess deleteUserProcess;
	
	@Autowired
	private GetWebviewIdProcess getWebviewIdProcess;
	
	@Autowired
	private CreateUserProcess createUserProcess;
	
	@Autowired
	private GetResultProcess getResultProcess;
	
	@Autowired
	private GetVerificationProcess getVerificationProcess;
	
	@Autowired
	private MigrationUserProcess migrationUserProcess;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "serverTask")
	public void serverJob(String param) throws Exception {
		log.info("KANG-20200908 >>>>> START param = {}, {}", param, CurrentInfo.get());
		
		LnsStreamPacket lnsStreamPacket = null;
		if (Flag.flag) {
			lnsStreamPacket = this.lnsStreamPacketQueue.get();  // blocking
			log.info("KANG-20200907 >>>>> lnsStreamPacket: REMOTE_INFO = {}", lnsStreamPacket);
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				do {
					// recv
					reqLnsStream = lnsStreamPacket.recvStream();
					if (Flag.flag) JsonPrint.getInstance().printPrettyJson("REQ.lnsStream", reqLnsStream);
					
					// process
					switch (reqLnsStream.getTypeCode()) {
					case "0200100":  // checkUser
						resLnsStream = this.checkUserProcess.process(reqLnsStream);
						break;
					case "0200200":  // getCalculation
						resLnsStream = this.getCalculationProcess.process(reqLnsStream);
						break;
					case "0200300":  // deleteUser
						resLnsStream = this.deleteUserProcess.process(reqLnsStream);
						break;
					case "0200400":  // getWebviewId
						resLnsStream = this.getWebviewIdProcess.process(reqLnsStream);
						break;
					case "0200500":  // createUser
						resLnsStream = this.createUserProcess.process(reqLnsStream);
						break;
					case "0200600":  // getResult
						resLnsStream = this.getResultProcess.process(reqLnsStream);
						break;
					case "0200700":  // getVerification
						resLnsStream = this.getVerificationProcess.process(reqLnsStream);
						break;
					case "0200800":  // migrationUser
						resLnsStream = this.migrationUserProcess.process(reqLnsStream);
						break;
					case "0200001":  // ping
					case "0200002":  // encrypt
					case "0200003":  // decrypt
						break;
					default:
						log.error("ERROR >>>>> WRONG TypeCode: {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
						break;
					}
					
					// send
					lnsStreamPacket.sendStream(resLnsStream);
					if (Flag.flag) JsonPrint.getInstance().printPrettyJson("RES.lnsStream", resLnsStream);
				} while (true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.info("KANG-20200907 >>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
