package org.tain.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStream;
import org.tain.object.lns.LnsStreamPacket;
import org.tain.queue.LnsQueueObject;
import org.tain.queue.LnsSendQueue;
import org.tain.queue.LnsStreamPacketQueue;
import org.tain.queue.WakeClientTaskQueue;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientJob {

	@Autowired
	private WakeClientTaskQueue wakeClientTaskQueue;
	
	@Autowired
	private LnsStreamPacketQueue lnsStreamPacketQueue;
	
	@Autowired
	private LnsSendQueue lnsSendQueue;
	
	///////////////////////////////////////////////////////////////////////////
	
	@Async(value = "clientTask")
	public void clientJob(String param) throws Exception {
		log.info("KANG-20200907 >>>>> START param = {}, {}", param, CurrentInfo.get());
		
		LnsStreamPacket lnsStreamPacket = null;
		if (Flag.flag) {
			lnsStreamPacket = this.lnsStreamPacketQueue.get();
		}
		
		if (Flag.flag) {
			try {
				while (true) {
					LnsQueueObject lnsQueueObject = (LnsQueueObject) this.lnsSendQueue.get();
					
					// send
					LnsStream reqLnsStream = lnsQueueObject.getLnsStream();
					lnsStreamPacket.sendStream(reqLnsStream);
					
					// recv
					LnsStream resLnsStream = lnsStreamPacket.recvStream();
					lnsQueueObject.getLnsRecvQueue().set(resLnsStream);
				}
			} catch (Exception e) {
				//e.printStackTrace();
				log.error("ERROR >>>>> {}", e.getMessage());
			} finally {
				lnsStreamPacket.close();
			}
		}
		
		log.info("KANG-20200907 >>>>> END   param = {}, {}", param, CurrentInfo.get());
		
		if (Flag.flag) this.wakeClientTaskQueue.set(null);  // notify
	}
}
