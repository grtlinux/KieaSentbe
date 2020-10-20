package org.tain.task;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
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
public class ServerMainJob {

	private final String TITLE = "SERVER_MAIN_JOB ";
	
	@Autowired
	private SocketTicketReadyQueue socketTicketReadyQueue;
	
	@Autowired
	private SocketTicketUseQueue socketTicketUseQueue;
	
	@Autowired
	private ProjEnvJobProperties projEnvJobProperties;
	
	//@Autowired
	//private CallbackRestController callbackRestController;
	
	@Async(value = "serverMainTask")
	public void serverMainJob(String param) throws Exception {
		log.info(TITLE + ">>>>> START param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) {
			// server
			ServerSocket serverSocket = new ServerSocket();
			int port = this.projEnvJobProperties.getListenPort();
			InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", port);
			serverSocket.bind(inetSocketAddress);
			
			try {
				log.info(TITLE + ">>>>> LISTENING for client connection with port={}....INFO = {}", port, inetSocketAddress);
				
				LnsSocketTicket lnsSocketTicket = null;
				while (true) {
					lnsSocketTicket = this.socketTicketReadyQueue.get();  // queue-block
					log.info(TITLE + ">>>>> {} waiting for your accept(connection)", lnsSocketTicket);
					
					Socket socket = serverSocket.accept();  // connect-block
					log.info(TITLE + ">>>>> {} has a connection.. OK!!!", lnsSocketTicket);
					
					// set socket to ticket
					lnsSocketTicket.set(socket);
					log.info(TITLE + ">>>>> {} has a socket. SET SOCKET.", lnsSocketTicket);
					
					this.socketTicketUseQueue.set(lnsSocketTicket);
					log.info(TITLE + ">>>>> {} go into the queue of socketTicketUseQueue.", lnsSocketTicket);
					
					// for callback...
					//this.callbackRestController.set(lnsSocketTicket);
					
					Sleep.run(1 * 1000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (serverSocket != null) try { serverSocket.close(); } catch (Exception e) {}
			}
		}
		
		log.info(TITLE + ">>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
