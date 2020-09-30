package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.queue.SocketProcessQueue;
import org.tain.task.process.CheckUserProcess;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ServerJob {

	private final String TITLE = "SERVER_JOB ";
	
	@Autowired
	private SocketProcessQueue socketProcessQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private CheckUserProcess checkUserProcess;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "serverTask")
	public void serverJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		LnsSocketTicket lnsSocketTicket = null;
		if (Flag.flag) {
			lnsSocketTicket = this.socketProcessQueue.get();  // blocking
			log.info(TITLE + ">>>>> socketTicket: INFO = {}", lnsSocketTicket);
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
					default:
						log.error(TITLE + "ERROR >>>>> WRONG TypeCode: {}", JsonPrint.getInstance().toPrettyJson(reqLnsStream));
						resLnsStream = this.checkUserProcess.process(reqLnsStream);
						break;
					}
					
					// send
					lnsSocketTicket.sendStream(resLnsStream);
					if (Flag.flag) log.info(TITLE + ">>>>> resLnsStream = {}", JsonPrint.getInstance().toPrettyJson(resLnsStream));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lnsSocketTicket.close();
				this.socketProcessQueue.set(lnsSocketTicket);
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
