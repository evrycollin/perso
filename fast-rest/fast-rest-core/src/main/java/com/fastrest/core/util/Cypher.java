package com.fastrest.core.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;

public class Cypher {

    private static boolean idCypher = true;
    
    public static void setIdCyphering(boolean cypher) {
	idCypher=cypher;
	
    }

    public static Object maskId(Principal principal, Class<?> type, Object id) {
	try {
	    return idCypher ? encrypt((principal != null ? principal.getName()
		    : "ANONYMOUS") + "_" + type.getSimpleName() + "_" + id)
		    : id;
	} catch (Throwable th) {
	    throw new RuntimeException(th);
	}
    }

    public static String unmaskId(String type, String encryptedId) {
	if (!idCypher) {
	    return encryptedId;
	}
	try {
	    String decrypter = decrypt(encryptedId);
	    return decrypter.substring(decrypter.lastIndexOf('_') + 1);
	} catch (Throwable th) {
	    throw new RuntimeException(th);
	}
    }

    private static String encrypt(String valueToEnc) throws Exception {
	Cipher c = Cipher.getInstance(ALGORITHM);
	c.init(Cipher.ENCRYPT_MODE, key);
	byte[] encValue = c.doFinal(valueToEnc.getBytes());
	String encryptedValue = Base64.encodeBase64String(encValue);
	return encryptedValue.replace('/', '_').replace('+', '-').replace('=', '!').trim();
    }

    private static String decrypt(String encryptedValue) throws Exception {
	Cipher c = Cipher.getInstance(ALGORITHM);
	c.init(Cipher.DECRYPT_MODE, key);
	byte[] decordedValue = Base64.decodeBase64(encryptedValue.replace('_', '/').replace('-','+').replace('!', '='));
	byte[] decValue = c.doFinal(decordedValue);
	String decryptedValue = new String(decValue);
	return decryptedValue;
    }

    private static final String ALGORITHM = "DES";
    static Key key;
    static {
	try {
	    KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
	    keyGen.init(new SecureRandom());
	    key = keyGen.generateKey();
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

}
