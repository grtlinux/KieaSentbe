package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsClientJobTicket;

@Component
public class ClientJobQueue {

	private final LinkedList<LnsClientJobTicket> queue = new LinkedList<>();
	
	public synchronized void set(LnsClientJobTicket object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized LnsClientJobTicket get() {
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
