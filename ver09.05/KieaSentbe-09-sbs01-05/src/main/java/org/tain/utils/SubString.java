package org.tain.utils;

import lombok.extern.slf4j.Slf4j;

// to NIO
@Slf4j
public class SubString {

	private String strStream = null;
	private int offset = 0;
	
	public SubString(String strStream) {
		this.strStream = strStream;
		this.offset = 0;
	}
	
	public String get(int size) {
		String ret = this.strStream.substring(this.offset, this.offset + size);
		log.trace("SubString >>>>> [%3d:%3d][%s]\n", this.offset, size, ret);
		this.offset += size;
		return ret;
	}
	
	////////////////////////////////////////////////////
	
	private boolean flagNull = true;
	
	public void setFlagNull(boolean flag) {
		this.flagNull = flag;
	}
	
	public boolean getFlagNull() {
		return this.flagNull;
	}
}
