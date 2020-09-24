package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsStreamPacket;

@Component
public class LnsStreamPacketQueue {

	private final LinkedList<LnsStreamPacket> queue = new LinkedList<>();
	
	public synchronized void set(LnsStreamPacket object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized LnsStreamPacket get() {
		while (this.queue.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		return this.queue.removeFirst();
	}
	
	public int size() {
		return this.queue.size();
	}
}
