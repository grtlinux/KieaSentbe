package org.tain.working;

import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;
import org.tain.utils.Flag;

import com.github.mervick.aes_everywhere.Aes256;

import lombok.extern.slf4j.Slf4j;

/*
 * https://github.com/mervick/aes-everywhere
 */
@Component
@Slf4j
public class AESeverywhereWorking {

	public void test01() throws Exception {
		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
		
		if (Flag.flag) {
			String text = "TEXT";
			String pass = "PASSWORD";

			byte[] text_bytes = text.getBytes("UTF-8");
			byte[] pass_bytes = pass.getBytes("UTF-8");

			System.out.println(">>>>> text: " + text);
			System.out.println(">>>>> pass: " + pass);
			
			// strings encryption
			String encrypted = Aes256.encrypt(text, pass);
			System.out.println(">>>>> encrypted: " + encrypted);

			// bytes encryption
			byte[] encrypted_bytes = Aes256.encrypt(text_bytes, pass_bytes);
			System.out.println(">>>>> encrypted_bytes: " + encrypted_bytes);

			// strings decryption
			String decrypted = Aes256.decrypt(encrypted, pass);
			System.out.println(">>>>> decrypted: " + decrypted);

			// bytes decryption
			byte[] decrypted_bytes = Aes256.decrypt(encrypted_bytes, pass_bytes);
			System.out.println(">>>>> decrypted_bytes: " + decrypted_bytes);
		}
	}
}
