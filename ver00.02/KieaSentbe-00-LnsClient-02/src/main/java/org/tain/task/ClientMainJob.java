package org.tain.task;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.properties.ProjEnvJobProperties;
import org.tain.queue.SocketTicketReadyQueue;
import org.tain.queue.SocketTicketUseQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientMainJob {

	private final String TITLE = "CLIENT_MAIN_JOB ";
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
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
					for (int retry_cnt = 1; ; retry_cnt ++) {
						try {
							socket = new Socket();
							InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
							socket.connect(inetSocketAddress);
							log.info(TITLE + ">>>>> {} has got a connection.. OK!!!", lnsSocketTicket);
							break;
						} catch (Exception e) {
							log.error(TITLE + "ERROR: retry_cnt = {}, msg = {}", retry_cnt, e.getMessage());
							Sleep.run(10 * 1000);
						}
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
