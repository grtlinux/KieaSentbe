package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.tain.object.lns.LnsSocketTicket;

@Component
public class LnsSocketProcessQueue {

	private final LinkedList<LnsSocketTicket> queue = new LinkedList<>();
	
	public synchronized void set(LnsSocketTicket object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized LnsSocketTicket get() {
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
