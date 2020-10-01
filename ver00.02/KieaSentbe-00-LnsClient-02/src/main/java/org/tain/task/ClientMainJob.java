package org.tain.task;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsSocketTicket;
import org.tain.properties.ProjEnvJobProperties;
import org.tain.queue.ClientProcessQueue;
import org.tain.queue.LnsSocketTicketQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientMainJob {

	private final String TITLE = "CLIENT_MAIN_JOB ";
	
	@Autowired
	private LnsSocketTicketQueue lnsSocketTicketQueue;
	
	@Autowired
	private ClientProcessQueue lnsSocketProcessQueue;
	
	@Autowired
	private ProjEnvJobProperties projEnvJobProperties;
	
	@Async(value = "clientMainTask")
	public void clientMainJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) {
			IntStream.rangeClosed(1, 1).forEach(index -> {
				LnsSocketTicket lnsSocketTicket = new LnsSocketTicket("TICKET-" + index);
				this.lnsSocketTicketQueue.set(lnsSocketTicket);
				log.info(TITLE + ">>>>> tichet is {}", lnsSocketTicket.getName());
			});
		}
		
		if (Flag.flag) {
			// client
			String host = this.projEnvJobProperties.getOnlineHost();
			int port = this.projEnvJobProperties.getOnlinePort();
			
			try {
				log.info(TITLE + ">>>>> CONNECTION CLIENT for [host,port]=[{},{}]", host, port);
				
				LnsSocketTicket lnsSocketTicket = null;
				while (true) {
					lnsSocketTicket = lnsSocketTicketQueue.get();  // queue-block
					log.info(TITLE + ">>>>> {} trying to get a connection", lnsSocketTicket.getName());
					
					Socket socket = new Socket();
					InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
					socket.connect(inetSocketAddress);
					log.info(TITLE + ">>>>> {} has got a connection.. OK!!!", lnsSocketTicket.getName());
					
					// set socket to ticket
					lnsSocketTicket.set(socket);
					log.info(TITLE + ">>>>> {} has a socket. SET SOCKET.", lnsSocketTicket.getName());
					
					this.lnsSocketProcessQueue.set(lnsSocketTicket);
					log.info(TITLE + ">>>>> {} go into the queue of lnsSocketProcessQueue.", lnsSocketTicket.getName());
					
					Sleep.run(1 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
