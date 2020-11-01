package org.tain.queue;

import java.util.LinkedList;

import org.springframework.stereotype.Component;

@Component
public class LnsSendQueue {

	private final LinkedList<Object> queue = new LinkedList<>();
	
	public synchronized void set(Object object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized Object get() {
		while (this.queue.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		return this.queue.removeFirst();
	}
	
	public synchronized void clear() {
		this.queue.clear();
	}
	
	public int size() {
		return this.queue.size();
	}
}
