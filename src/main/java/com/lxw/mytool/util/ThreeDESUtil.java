package com.lxw.mytool.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ThreeDESUtil { 
	// 算法名称 
	public static final String KEY_ALGORITHM = "desede"; 
	public static byte[] KEY = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7".getBytes();
    public static byte[] KEY_IV = { 1, 2, 3, 4, 5, 6, 7, 8 };
	// 算法名称/加密模式/填充方式 
	//加密模式：ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128
	//填充方式：Nopadding/PKCS5Padding/PKCS7Padding/ISO10126Padding/
	public static final String CIPHER_ALGORITHM = "desede/CBC/ISO10126Padding";

	/** 
	 * CBC加密 
	 * @param key 密钥 
	 * @param keyiv IV 
	 * @param data 明文 
	 * @return Base64编码的密文 
	 * @throws Exception 
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
//	    Security.addProvider(new BouncyCastleProvider());
	    Key deskey = keyGenerator(new String(key));
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    IvParameterSpec ips = new IvParameterSpec(keyiv);
	    cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
	    byte[] bOut = cipher.doFinal(data);
	    for (int k = 0; k < bOut.length; k++) {
	        System.out.print(bOut[k] + " ");
	    }
	    System.out.println("");
	    return bOut;
	}

	/** 
	 *   
	 * 生成密钥key对象 
	 * @param keyStr 密钥字符串
	 * @return 密钥对象 
	 * @throws InvalidKeyException   
	 * @throws NoSuchAlgorithmException   
	 * @throws InvalidKeySpecException   
	 * @throws Exception 
	 */
	private static Key keyGenerator(String keyStr) throws Exception {
	    byte input[] = HexString2Bytes(keyStr);
	    DESedeKeySpec KeySpec = new DESedeKeySpec(input);
	    SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
	    return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
	}

	private static int parse(char c) {
	    if (c >= 'a') return (c - 'a' + 10) & 0x0f;
	    if (c >= 'A') return (c - 'A' + 10) & 0x0f;
	    return (c - '0') & 0x0f;
	}
	
	// 从十六进制字符串到字节数组转换 
	public static byte[] HexString2Bytes(String hexstr) {
	    byte[] b = new byte[hexstr.length() / 2];
	    int j = 0;
	    for (int i = 0; i < b.length; i++) {
	        char c0 = hexstr.charAt(j++);
	        char c1 = hexstr.charAt(j++);
	        b[i] = (byte) ((parse(c0) << 4) | parse(c1));
	    }
	    return b;
	}

	/** 
	 * CBC解密 
	 * @param key 密钥 
	 * @param keyiv IV 
	 * @param data Base64编码的密文 
	 * @return 明文 
	 * @throws Exception 
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
	    Key deskey = keyGenerator(new String(key));
	    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
	    IvParameterSpec ips = new IvParameterSpec(keyiv);
	    cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
	    byte[] bOut = cipher.doFinal(data);
	    return bOut;
	}
	/**
	 * 还原密码
	 */
	public static String unEncryptPassword(String encryptPassword) throws Exception {
		byte[] byteEncryptPass = new sun.misc.BASE64Decoder().decodeBuffer(encryptPassword);
		String unEncryptPass = new String(ThreeDESUtil.des3DecodeCBC(ThreeDESUtil.KEY, ThreeDESUtil.KEY_IV, byteEncryptPass));
		return unEncryptPass;
	}
	
	public static void main(String[] args) throws Exception {
	    
	    byte[] data = "123456".getBytes("UTF-8");
	    System.out.println("data.length=" + data.length);
	    System.out.println("CBC加密解密");
	    byte[] str5 = des3EncodeCBC(KEY, KEY_IV, data);
	    System.out.println("00000:" + new sun.misc.BASE64Encoder().encode(str5));
	    System.out.println("11111:" + String.valueOf(str5).toString());
	    byte[] str7 = des3EncodeCBC(KEY, KEY_IV, data);
	    System.out.println("22222:" + new sun.misc.BASE64Encoder().encode(str7));
	    
	    byte[] str6 = des3DecodeCBC(KEY, KEY_IV, str5);
	    System.out.println("33333:" + new String(str6, "UTF-8"));
	    
	    System.out.println(unEncryptPassword("bG/Eel4wLJg="));
	}
} 