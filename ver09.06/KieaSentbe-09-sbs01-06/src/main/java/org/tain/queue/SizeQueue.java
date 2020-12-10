package org.tain.queue;

import java.util.LinkedList;

public class SizeQueue {

	private final int MAX_SIZE = 100;
	
	private final LinkedList<Object> queue = new LinkedList<>();
	private int size = 0;
	
	///////////////////////////////////////////////////////////////
	
	public SizeQueue(int size) {
		if (size < 0) {
			size = 0;
		} else if (size > MAX_SIZE) {
			size = MAX_SIZE;
		}
		this.size = size;
	}
	
	public SizeQueue() {
		this(0);
	}
	
	///////////////////////////////////////////////////////////////
	
	public synchronized void set(Object object) {
		this.queue.addLast(object);
		this.notifyAll();
	}
	
	public synchronized Object get() {
		while (this.queue.size() <= this.size) {
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
