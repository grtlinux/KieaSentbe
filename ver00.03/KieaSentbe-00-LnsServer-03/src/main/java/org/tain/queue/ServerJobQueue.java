package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsServerJobTicket;

@Component
public class ServerJobQueue {

	private final LinkedList<LnsServerJobTicket> queue = new LinkedList<>();
	
	public synchronized void set(LnsServerJobTicket object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized LnsServerJobTicket get() {
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
