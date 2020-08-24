package org.tain.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class StbCrypt {

	private static StbCrypt instance = null;
	
	public static synchronized StbCrypt getInstance() throws Exception {
		if (instance == null) {
			instance = new StbCrypt();
		}
		return instance;
	}
	
	//////////////////////////////////////////////////
	
	private static String ALG = "AES/CBC/PKCS5Padding";
	//private static String PK = "01234567890123456789012345678901"; // 32bytes
	private static String PK = "cXdqZmlvcWVqd2xd2pmam9pZaG9nZnFl"; // 32bytes
	private static String IV = PK.substring(0, 16); // 16bytes

	private Cipher cipher = null;
	private SecretKeySpec keySpec = null;
	private IvParameterSpec ivParamSpec = null;
	
	private StbCrypt() throws Exception {
		this.cipher = Cipher.getInstance(ALG);
		this.keySpec = new SecretKeySpec(IV.getBytes(), "AES");
		this.ivParamSpec = new IvParameterSpec(IV.getBytes());
	}
	
	public String encrypt(String plainText) throws Exception {
		this.cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);;
		
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public String decrypt(String cipherText) throws Exception {
		this.cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);;
		
		byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
		byte[] decrypted = cipher.doFinal(decodedBytes);
		return new String(decrypted, "UTF-8");
	}
}
