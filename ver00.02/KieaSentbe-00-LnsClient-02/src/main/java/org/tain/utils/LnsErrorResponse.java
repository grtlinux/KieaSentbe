package org.tain.utils;

public class LnsErrorResponse {

	private String code;
	private String message;
	
	public LnsErrorResponse(String messaage) {
		System.out.println("LnsErrorResponse >>>>> " + message);
		this.code = message.substring(0, 4);
		int pos1 = message.indexOf('[');
		int pos2 = message.lastIndexOf(']');
		this.message = message.substring(pos1 + 1, pos2);
	}
	
	public String getCode() {
		return this.code;
	}
	public String getMessage() {
		return this.message;
	}
}
