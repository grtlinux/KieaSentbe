package org.tain.working;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tain.properties.LnsEnvJobProperties;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HmacWorking {

	@Autowired
	private LnsEnvJobProperties lnsEnvJobProperties;
	
	public void printSample() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String key = this.lnsEnvJobProperties.getSentbeSecretKey();  // Secret-Key
			String message = "the message to hash here";

			Mac hasher = Mac.getInstance("HmacSHA256");
			hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));   // Secret-Key

			byte[] hash = hasher.doFinal(message.getBytes());               // message

			// to lowercase hexits and base64
			System.out.println(">>>>> HMAC.Hexit  [" + DatatypeConverter.printHexBinary(hash) + "]");     // Hexit
			System.out.println(">>>>> HMAC.Base64 [" + DatatypeConverter.printBase64Binary(hash) + "]");  // Base64
		}
	}
}
