package org.tain.task;

import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStreamPacket;
import org.tain.properties.ProjEnvJobProperties;
import org.tain.queue.LnsStreamPacketQueue;
import org.tain.queue.WakeClientTaskQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.Sleep;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientMainJob {

	@Autowired
	private WakeClientTaskQueue wakeClientTaskQueue;
	
	@Autowired
	private ProjEnvJobProperties projEnvJobProperties;
	
	@Autowired
	private LnsStreamPacketQueue lnsStreamPacketQueue;
	
	@Async(value = "clientMainTask")
	public void clientMainJob(String param) throws Exception {
		log.info("KANG-20200907 >>>>> START param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) {
			// client
			String host = this.projEnvJobProperties.getOnlineHost();
			int port = this.projEnvJobProperties.getOnlinePort();
			
			while (true) {
				InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
				
				try {
					log.info(">>>>> connection....INFO = {}", inetSocketAddress);
					Socket socket = new Socket();
					socket.connect(inetSocketAddress);
					log.info(">>>>> connection is OK!!!");
					
					if (Flag.flag) this.lnsStreamPacketQueue.set(new LnsStreamPacket(socket));
				} catch (Exception e) {
					//e.printStackTrace();
					log.error(">>>>> ERROR: {}", e.getMessage());
					Sleep.run(10 * 1000);
					continue;
				}
				
				Sleep.run(10 * 1000);
				if (Flag.flag) this.wakeClientTaskQueue.get();  // blocking
			}
		}
		
		log.info("KANG-20200907 >>>>> END   param = {}, {}", param, CurrentInfo.get());
	}
}
