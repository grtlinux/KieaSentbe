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
import org.tain.task.process.ApiProcess;
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
	
	@Autowired
	private ApiProcess apiProcess;
	
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
					resLnsStream = this.apiProcess.process(reqLnsStream);
					
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
	
	///////////////////////////////////////////////////////////////////////////
	
	//@Async(value = "serverTask")
	@Deprecated
	public void serverJob_old(LnsInfoTicket infoTicket) throws Exception {
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
					case "0700100":  // checkUser
						resLnsStream = this.checkUserProcess.process(reqLnsStream);
						break;
					case "0700200":  // getCalculation
						resLnsStream = this.getCalculationProcess.process(reqLnsStream);
						break;
					case "0700300":  // deleteUser
						resLnsStream = this.deleteUserProcess.process(reqLnsStream);
						break;
					case "0700400":  // getWebviewId
						resLnsStream = this.getWebviewIdProcess.process(reqLnsStream);
						break;
					case "0700500":  // createUser
						resLnsStream = this.createUserProcess.process(reqLnsStream);
						break;
					case "0700600":  // getResult
						resLnsStream = this.getResultProcess.process(reqLnsStream);
						break;
					case "0700700":  // getVerification
						resLnsStream = this.getVerificationProcess.process(reqLnsStream);
						break;
					case "0700800":  // migrationUser
						resLnsStream = this.migrationUserProcess.process(reqLnsStream);
						break;
					case "0700001":  // ping
					case "0700002":  // encrypt
					case "0700003":  // decrypt
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
