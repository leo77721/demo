package com.example.demo.test;

import org.apache.axis.encoding.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Frank XU
 * 2016年10月6日
 *
 */
public class Encryption {

	private final static String KEY = "HONGTENG";
	private final static String IV = "23398525";
	
	/**
	 * 加密
	 * @param encryptString 待加密串
	 * @param key	秘钥
	 * @param iv	偏移量
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String encryptString, String key, String iv)
			throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
		return Base64.encode(encryptedData);
	}
	/**
	 * 
	 * @param decryptString
	 * @param key
	 * @param iv
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String decryptString) throws Exception {
		return decrypt(decryptString, KEY, IV);
	}
	/**
	 * 解密
	 * @param decryptString	待解密串
	 * @param key	秘钥
	 * @param iv	偏移量
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String decryptString, String key, String iv) throws Exception {

			byte[] byteMi = Base64.decode(decryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData, "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		String str = encrypt("HNAPUser", KEY,IV);
		//String decrypt = decrypt("U+X2EnZA2GoJUznzXq8zfA==", KEY,IV);
		String decrypt1 = decrypt("sXeRV25eJlvCtJxuA9H3sw==", KEY,IV);
		System.out.println(decrypt1 );
	}
}
