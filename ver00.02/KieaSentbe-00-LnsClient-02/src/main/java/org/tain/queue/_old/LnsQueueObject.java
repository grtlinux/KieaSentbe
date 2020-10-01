package org.tain.queue._old;

import org.tain.object.lns.LnsStream;
import org.tain.queue.LnsRecvQueue;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LnsQueueObject {

	private LnsStream lnsStream;
	private LnsRecvQueue lnsRecvQueue;
	
	@Builder
	public LnsQueueObject(
			LnsStream lnsStream,
			LnsRecvQueue lnsRecvQueue
			) {
		this.lnsStream = lnsStream;
		this.lnsRecvQueue = lnsRecvQueue;
	}
}
