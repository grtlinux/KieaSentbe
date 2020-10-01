package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;
import org.tain.object.ticket.LnsInfoTicket;

@Component
public class InfoTicketReadyQueue {

	private final LinkedList<LnsInfoTicket> queue = new LinkedList<>();
	
	public synchronized void set(LnsInfoTicket object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized LnsInfoTicket get() {
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
