package org.tain.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LnsTimeZone {

	/*
	 * Id = UTC+9
	 * ZoneId = Asia/Seoul
	 */
	public static void setTimeZone(String strId) {
		if (Flag.flag) {
			TimeZone.setDefault(TimeZone.getTimeZone(strId));
			//TimeZone.setDefault(TimeZone.getTimeZone("UTC+9"));
			//TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
			//TimeZone.setDefault(TimeZone.getTimeZone("GMT+09:00"));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	
	/*
	 * REF: https://www.daleseo.com/java8-zoned-date-time/
	 */
	public static void printInfo() {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get(), LocalDateTime.now());
		
		if (Flag.flag) {
			ZoneOffset seoulZoneOffset = ZoneOffset.of("+90:00");
			log.info(">>>>> +9000 Time = {}", ZonedDateTime.now(seoulZoneOffset));
			ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
			log.info(">>>>> Seoul Time = {}", ZonedDateTime.now(seoulZoneId));
		}
	}
}
