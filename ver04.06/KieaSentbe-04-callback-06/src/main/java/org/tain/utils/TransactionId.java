package org.tain.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionId {

	//private static Random random = new Random(System.currentTimeMillis());
	private static int seq = 0;
	
	public synchronized static String get() {
		StringBuffer transactionId = new StringBuffer();
		if (Flag.flag) {
			LocalDateTime now = LocalDateTime.now();
			transactionId.append("HW");
			transactionId.append(now.format(DateTimeFormatter.ofPattern("yyMMddHHmm")));
			transactionId.append("A");
			transactionId.append(now.format(DateTimeFormatter.ofPattern("ss")));
			//trId.append(String.valueOf(random.nextInt(10)));
			transactionId.append(String.valueOf(++seq % 10));
		}
		
		return transactionId.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private static String ENGLISH_LOWER = "abcdefghijklmnopqrstuvwxyz";
	private static String ENGLISH_UPPER = ENGLISH_LOWER.toUpperCase();
	//private static String NUMBER = "0123456789";
	//private static String DATA_FOR_RANDOM_STRING = ENGLISH_LOWER + ENGLISH_UPPER + NUMBER;
	private static String DATA_FOR_RANDOM_STRING = ENGLISH_UPPER;
	
	private static SecureRandom random = new SecureRandom();
	private static int length = 5;
	
	public synchronized static String getRandString() {
		if (length < 1)
				throw new IllegalArgumentException("length must be a positive number.");
		
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(DATA_FOR_RANDOM_STRING.charAt(random.nextInt(DATA_FOR_RANDOM_STRING.length())));
		}
		return sb.toString();
	}
}
