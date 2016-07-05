package com.casewaresa.framework.util;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.casewaresa.framework.contract.IConstantes;

public class CryptUtil {
	
	public static String ByteToHex(byte byteData[]){
		//convert the byte to hex format
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<byteData.length;i++) {
			String hex=Integer.toHexString(0xff & byteData[i]);
		 	if(hex.length()==1) hexString.append("0");
		 	hexString.append(hex);
		}
		
		return hexString.toString().toUpperCase();		
	}
	
	public static byte[] HexToByte(String hex){
		return new BigInteger(hex, 16).toByteArray();
	}	
	
	public static String encrypt(String Data) throws Exception {
		Key key = new SecretKeySpec(IConstantes.AES_KEY.getBytes("UTF-8"), IConstantes.AES_FACTORY);
		Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING"); 

		cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] byteData = cipher.doFinal(Data.getBytes("UTF-8"));
        
        return ByteToHex(byteData);   
    }

    public static String decrypt(String data) throws Exception {
		Key key = new SecretKeySpec(IConstantes.AES_KEY.getBytes("UTF-8"), IConstantes.AES_FACTORY);

    	Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5PADDING"); 
		
		cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decValue = cipher.doFinal(HexToByte(data));

        return new String(decValue);
    }
    
	public static String getMD5(String password) throws Exception{
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		md.update(password.getBytes("UTF-8"));
 
		return ByteToHex(md.digest()); 
    }	
}