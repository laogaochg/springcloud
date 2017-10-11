/**
 * 
 */
package com.nanhang.mall.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author 706090
 * 
 */
public class Encrypt {

	private static final String Algorithm = "DESede";
	public static final byte[] keyBytes ="This is a secret keynews".getBytes();

	private static Encrypt encrypt = new Encrypt();
	
	public static Encrypt getInstance()
	{
		return encrypt;
	}
	
	// 24字节的密钥

	/*
	 * @ use DESede algorithm to enc the src
	 * 
	 * @ keybyte: secretkey 24 byte
	 * 
	 * @ src:the text needs to be encrypt
	 * 
	 * @ return the enc result
	 */
	public static byte[] encryptMode(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	 
	//解密
	public String decrypt(byte[] src) throws Exception
	{
		String srcString = new String(src,"UTF-8");
		byte[] srcBytes = decryptMode(decode(srcString));
		return new String(srcBytes,"UTF-8");
	}
	
	//加密
	public byte[] encrypt(String src) throws Exception
	{
		String srcString = encode(encryptMode(src.getBytes("UTF-8")));
		return srcString.getBytes("UTF-8");
	}
	/*
	 * @ use DESede algorithm to dec the src
	 * 
	 * @ keybyte: secretkey 24 byte
	 * 
	 * @ src:the text needs to be dec
	 * 
	 * @ return the dec result
	 */
	public  byte[] decryptMode(byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public void writeFile(String src) throws Exception {
		File file = new File("C://encrypt.txt");
		FileWriter fw = new FileWriter(file);
		fw.write(src);
		fw.close();
	}

	public String readFile(String path) {
		File file = new File(path);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		char[] cbuf = new char[10240];
		try {
			fr.read(cbuf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res = new String(cbuf);
		return res;
	}

	/**
	 * 编码
	 * 
	 * @param bstr
	 * @return String
	 */
	public  String encode(byte[] bstr) {
		return new sun.misc.BASE64Encoder().encode(bstr);
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public  byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}
}
