package org.tain.queue;

import org.tain.object.lns.LnsStream;

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
