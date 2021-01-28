package org.tain.working.errorTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.data.LnsError;
import org.tain.task.ErrorReaderJob;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;
import org.tain.utils.JsonPrint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorTest01Working {

	/*
	 *     Regular_Expression   Description
	 *     .                    어떤문자 1개를 의미.
	 *     ^regex               ^다음 regex로 라인을 시작.
	 *     regex$               regex가 라인의 마지막.
	 *     [abc]                a, b, c 중 하나.
	 *     [abc][vz]            포함하는 2개의 문자.
	 *     [^abc]               제외한 1개의 문자.
	 *     [a-d1-7]             a~d, 1~7을 중에 1개의 문자.
	 *     X|Z                  X 또는 Z
	 *     \d                   [0~9]와 동일.
	 *     \D                   숫자가 아닌 어떤 문자. [^0~9]
	 *     \s                   whitespace 1개. [\t\r\n\x0b\f]
	 *     \S                   whitespace를 제외한 1개 문자.
	 *     \w                   Alphanumeric(alphabet, number) 1개. [a-zA-Z_0-9]
	 *     \W                   Alphanumeric을 제외한 문자. whitespace 포함.
	 *     \S+                  whitespace를 제외한 여러 문자.
	 *     \b                   단어의 경계(공백)을 찾습니다.
	 *     
	 *     *                    0, 1, 2 ...
	 *     +                    1, 2, ...
	 *     ?                    0, 1
	 *     {X}                  X 번 반복.
	 *     {X,Y}                X~Y 번 반복.
	 *     *?                   가장 적게 일치하는 패턴을 찾음.
	 */
	public void test00() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String pattern = null;
			
			pattern = ".*Ab.";
			if (Flag.flag) log.info("1. regex: {} {}", "123  Abc".matches(pattern), " Abc".matches(pattern));
			
			pattern = "ab\\s+\\S";
			if (Flag.flag) log.info("2. regex: {} {} {}", " ab  ".matches(pattern), "ab c".matches(pattern), "ab      c".matches(pattern));
			
			pattern = ".*\\btimeout\\b.*";
			if (Flag.flag) log.info("2. regex: {}", "connection is timeout.".matches(pattern));
		}
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ErrorReaderJob errorReaderJob;
	
	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			//if (Flag.flag) log.info(">>>>> lnsError = {}", this.errorReaderJob.get());
			if (Flag.flag) log.info(">>>>> lnsError = {}", this.errorReaderJob.search("connection is timeout."));
			if (Flag.flag) log.info(">>>>> lnsError = {}", this.errorReaderJob.search("[3rd party] error exception..."));
			if (Flag.flag) log.info(">>>>> lnsError = {}", this.errorReaderJob.search("1003 NOTMATCH USER EXIST.."));
		}
		
		if (Flag.flag) {
			if (Flag.flag) log.info(">>>>> mapError = {}", JsonPrint.getInstance().toPrettyJson(this.errorReaderJob.getMap()));
		}
		
		if (Flag.flag) {
			LnsError lnsError = this.errorReaderJob.search("AccessToken is not allowed....");
			if (Flag.flag) log.info("* error_org     : {}", lnsError.getError_org());
			if (Flag.flag) log.info("* error_server  : {}", lnsError.getError_server());
			if (Flag.flag) log.info("* error_code    : {}", lnsError.getError_code());
			if (Flag.flag) log.info("* error_message : {}", lnsError.getError_message());
			if (Flag.flag) log.info("* error_msg     : {}", lnsError.getError_msg());
			if (Flag.flag) log.info("* error_msg2    : {}", String.format("%7.7s", lnsError.getError_msg()));
			if (Flag.flag) log.info("* error_regex   : {}", lnsError.getError_regex());
			if (Flag.flag) log.info("* comment       : {}", lnsError.getComment());
			if (Flag.flag) log.info("* error_key     : {}", lnsError.getKey());
		}
		
		if (Flag.flag) {
			LnsError lnsError = null;
			lnsError = this.errorReaderJob.search("Return Success....");
			lnsError = this.errorReaderJob.search("SUCCESS");
			lnsError = this.errorReaderJob.search("msg success....");
			if (Flag.flag) log.info("* error_org     : {}", lnsError.getError_org());
			if (Flag.flag) log.info("* error_server  : {}", lnsError.getError_server());
			if (Flag.flag) log.info("* error_code    : {}", lnsError.getError_code());
			if (Flag.flag) log.info("* error_message : {}", lnsError.getError_message());
			if (Flag.flag) log.info("* error_msg     : {}", lnsError.getError_msg());
			if (Flag.flag) log.info("* error_msg2    : {}", String.format("%7.7s", lnsError.getError_msg()));
			if (Flag.flag) log.info("* error_regex   : {}", lnsError.getError_regex());
			if (Flag.flag) log.info("* comment       : {}", lnsError.getComment());
			if (Flag.flag) log.info("* error_key     : {}", lnsError.getKey());
		}
	}
}
