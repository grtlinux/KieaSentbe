package org.tain.task;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.properties.ProjEnvJobProperties;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsSendQueue;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.queue.SocketTicketUseQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientMainJob {

	private final String TITLE = "CLIENT_MAIN_JOB ";
	
	private final int MAX_RETRY_CNT = 3;
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	@Autowired
	private ProjEnvJobProperties projEnvJobProperties;
	
	@Async(value = "clientMainTask")
	public void clientMainJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) {
			// client
			String host = this.projEnvJobProperties.getOnlineHost();
			int port = this.projEnvJobProperties.getOnlinePort();
			
			try {
				log.info(TITLE + ">>>>> CONNECTION CLIENT for [host:port]=[{}:{}]", host, port);
				
				LnsSocketTicket lnsSocketTicket = null;
				while (true) {
					lnsSocketTicket = this.socketTicketReadyQueue.get();  // queue-block
					log.info(TITLE + ">>>>> {} trying to get a connection", lnsSocketTicket);
					
					Socket socket = null;
					int retry_cnt = 0;
					for (; retry_cnt < MAX_RETRY_CNT; retry_cnt ++) {
						try {
							socket = new Socket();
							InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
							socket.connect(inetSocketAddress);
							log.info(TITLE + ">>>>> {} has got a connection.. OK!!!", lnsSocketTicket);
							break;
						} catch (Exception e) {
							//ERROR: retry_cnt = {}, msg = Connection refused (Connection refused)
							log.error(TITLE + "ERROR: retry_cnt = {}, msg = {}", retry_cnt, e.getMessage());
							Sleep.run(10 * 1000);
						}
					}
					if (retry_cnt >= MAX_RETRY_CNT) {
						// because of unable to connect, return error message
						int sizeQueue = this.lnsSendQueue.size();
						for (int i = 0; i < sizeQueue; i++) {
							LnsQueueObject reqLnsQueueObject = (LnsQueueObject) this.lnsSendQueue.get();
							
							String resStrData = String.format("99999couldn't connect to server");
							String resTypeCode = "0210100";
							String resLen = String.format("%04d", 7 + resStrData.length());
							LnsStream resLnsStream = new LnsStream(resLen + resTypeCode + resStrData);
							log.info(TITLE + ">>>>> ({}) ERROR.lnsStrem = {}", i+1, JsonPrint.getInstance().toPrettyJson(resLnsStream));
							
							reqLnsQueueObject.getLnsRecvQueue().set(resLnsStream);
						}
						this.lnsSendQueue.clear();
						
						this.socketTicketReadyQueue.set(lnsSocketTicket);
						continue;
					}
					
					// set socket to ticket
					lnsSocketTicket.set(socket);
					log.info(TITLE + ">>>>> {} has a socket. SET SOCKET.", lnsSocketTicket);
					
					this.socketTicketUseQueue.set(lnsSocketTicket);
					log.info(TITLE + ">>>>> {} go into the queue of socketTicketUseQueue.", lnsSocketTicket);
					
					Sleep.run(1 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
