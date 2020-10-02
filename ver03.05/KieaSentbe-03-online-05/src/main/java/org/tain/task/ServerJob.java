package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.ticket.LnsInfoTicket;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.InfoTicketReadyQueue;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.queue.SocketTicketUseQueue;
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

	private final String TITLE = "SERVER_JOB ";
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private InfoTicketReadyQueue infoTicketReadyQueue;
	
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
	public void serverJob(LnsInfoTicket infoTicket) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", infoTicket, CurrentInfo.get());
		
		LnsSocketTicket lnsSocketTicket = null;
		if (Flag.flag) {
			lnsSocketTicket = this.socketTicketUseQueue.get();  // blocking
			log.info(TITLE + ">>>>> serverJob: INFO = {} {}", infoTicket, lnsSocketTicket);
		}
		
		////////////////////////////////////////////////////
		if (Flag.flag) {
			try {
				LnsStream reqLnsStream = null;
				LnsStream resLnsStream = null;
				while (true) {
					// recv
					reqLnsStream = lnsSocketTicket.recvStream();
					if (Flag.flag) log.info(TITLE + ">>>>> reqLnsStream = {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
					
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
						log.error(TITLE + "ERROR >>>>> WRONG TypeCode: {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
						break;
					}
					
					// send
					lnsSocketTicket.sendStream(resLnsStream);
					if (Flag.flag) log.info(TITLE + ">>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
				}
			} catch (Exception e) {
				//e.printStackTrace();
				// ERROR >>>>> ERROR: return value of read is negative(-)...
				log.error(TITLE + " ERROR >>>>> {}", e.getMessage());
			} finally {
				lnsSocketTicket.close();
			}
		}
		
		if (Flag.flag) {
			// return the tickets.
			this.socketTicketReadyQueue.set(lnsSocketTicket);
			this.infoTicketReadyQueue.set(infoTicket);
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", infoTicket, CurrentInfo.get());
	}
}
