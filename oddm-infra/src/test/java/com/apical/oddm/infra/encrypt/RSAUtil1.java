package com.apical.oddm.infra.encrypt;


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
public class RSAUtil1 {
	private static final Logger  logger = LoggerFactory.getLogger(RSAUtil1.class);
	private static KeyPair keyPair = null;  //  现在App用同一个RSA KeyPair
	private static final int KEY_SIZE = 1024; // 这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低, 这个值对应js里面一个MaxDigit值
	//前台的解释  :  maxDigits:
	//Change this to accommodate your largest number size. Use setMaxDigits() to change it!
	//In general, if you're working with numbers of size N bits, you'll need 2*N
	//bits of storage. Each digit holds 16 bits. So, a 1024-bit key will need 1024 * 2 / 16 = 128 digits of storage.
	private static String charsetName = "UTF-8";
	
	/**
	 * 生成密钥对 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	private static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			keyPair = keyPairGen.generateKeyPair();

			HashMap<String, String> keyRSA = new HashMap<String, String>();
			keyRSA.put("privateModulus",  ((RSAPrivateKey)(keyPair.getPrivate())).getModulus().toString(16));
			keyRSA.put("privateExponent", ((RSAPrivateKey)(keyPair.getPrivate())).getPrivateExponent().toString(16));
			keyRSA.put("publicModulus",   ((RSAPublicKey)(keyPair.getPublic())).getModulus().toString(16));
			keyRSA.put("publicExponent",  ((RSAPublicKey)(keyPair.getPublic())).getPublicExponent().toString(16));
			System.out.println("=================================start_key  " + new Date()+ "==========================================");
			System.out.println("=============publicModulus===" + keyRSA.get("publicModulus"));
			System.out.println("=============publicExponent==" + keyRSA.get("publicExponent"));
			System.out.println("=============privateModulus==" + keyRSA.get("privateModulus"));
			System.out.println("=============privateExponent=" + keyRSA.get("privateExponent"));
			System.out.println("==================================end_key=============================================");

			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 取公钥
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKey (){
		try {
			if (keyPair == null) {
				generateKeyPair();
			}
			return (RSAPublicKey)(keyPair.getPublic());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 取私钥
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey () throws Exception {
		if (keyPair == null) {
			generateKeyPair();
		}
		return (RSAPrivateKey)(keyPair.getPrivate());
	}
	
	/**
	 * 生成公钥  
	 * @param modulus
	 * @param publicExponent
	 * @return RSAPublicKey 
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus, 16);
			BigInteger b2 = new BigInteger(exponent, 16);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * * 生成私钥 
	 * @param modulus
	 * @param privateExponent
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */	
	public static RSAPrivateKey generateRSAPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus, 16);  
            BigInteger b2 = new BigInteger(exponent, 16);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
 

	/**
	 * * 加密 *
	 * @param key 加密的密钥 *
	 * @param data 待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 解密 
	 * @param key 解密的密钥
	 * @param encryptData 已经加密的数据,(16进制串)
	 * @return 解密后的明文
	 * @throws Exception
	 */
	public static String decrypt(PrivateKey pk, String encryptData) throws Exception {
		try {
			 byte[] raw =hexStringToBytes(encryptData);
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, (RSAPrivateKey)pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return new String(bout.toByteArray(),charsetName) ;
		} catch (Exception e) {
			logger.error("=============RSA解密错误：", e);
			return null;
		}
	}
	
	/**
	 * 解密由JS加密的字符串。
	 * @param pk
	 * @param encryptData
	 * @return
	 * @throws Exception
	 */
	public static String decryptStringByJs(PrivateKey pk, String encryptData) throws Exception {
		return new StringBuffer(decrypt(pk, encryptData)).reverse().toString();
	}

	 public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * 将char转换为 byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
	}
    
	private static String byte2hex(byte [] buffer){  
        String h = "";  
        for(int i = 0; i < buffer.length; i++){  
            String temp = Integer.toHexString(buffer[i] & 0xFF);  
            if(temp.length() == 1){  
                temp = "0" + temp;  
            }
            h = h + temp;  
        }  
        return h;  
    }  
	public static void main(String[] args) {
		try {
			generateKeyPair();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) throws Exception {
		String publicModulus= "8fac1a16da1f9acef8354fe3f21d6815629ee3836be889f9806b6b8ec940ce2b3300988b8c1ef935bcc09d1ec9f018bf308be2a606badae2edde0d48f0ac74fc9ff25f26bce9202e0795ed47b406b6ca170991c93a4e5145e577eabaff77150890e78801fc2fbcb083dea0c4ece78de7be645536686c4c582950d33a35dee83d";
		String publicExponent= "10001";
		String privateModulus= "8fac1a16da1f9acef8354fe3f21d6815629ee3836be889f9806b6b8ec940ce2b3300988b8c1ef935bcc09d1ec9f018bf308be2a606badae2edde0d48f0ac74fc9ff25f26bce9202e0795ed47b406b6ca170991c93a4e5145e577eabaff77150890e78801fc2fbcb083dea0c4ece78de7be645536686c4c582950d33a35dee83d";
		String privateExponent = "7dac8eb9b49d76be5ab1cfab560b51137bcbfeb9e3f76a51bc7aba80111d45fad5c74ca8acae1f525944a76bda3ad27ad34d59b9df232dfad915a6cb49f3a0f44cafb289078a0f6fb45a5f120053b5e734cd6d91838cd5348bf34132bb5d0b41248c5beb661f35edc0cccc06685aa4f347be6fa8041ade17c1c3ec9cf8e22ee5";
		String test = "123456";
		RSAPublicKey  aa = generateRSAPublicKey(publicModulus, publicExponent);
		byte[] en_test = encrypt(aa, test.getBytes(charsetName));
		//这个是取反后的密文 String data = "62787a9379068f0da01f96d51858fa889b3eff4341ef48011c47eb88b8055eca7a19e269a0f485f4d7db083761f221d587f9acb8ef28756d24174d78c2c1b1beac8eab2b4939f736fe7362390fec4f2954033ec261e7144a26fe6093c590fa6602a4b1e297e1d8ea74d753e2931ede0c2440e0fc9647a59cb8aa0a587c5a8de2";
	    System.out.println(byte2hex(en_test));//字节数组打印
		String de_test = decrypt(generateRSAPrivateKey(privateModulus, privateExponent), byte2hex(en_test));
		System.out.println("================>>> "  + de_test);
	
		String jsdata = "09eda854e45f3b2298703e75d45bc6b61a9f3008db1e0dd68e730a0c33aa321998a65dcfd03b0e28ad9bdbefcc99f29b99c434ef9a67b2f58a16cfb70b6a8eebe9fc768895a3c093a43aa4629154f99616c87e6ce90a0dd1a408dd3d9e5d46e98eed4f9a14440546f005badd011b7efb5f36536c9d8f1682d8681e993e755b79";
		de_test = decrypt(generateRSAPrivateKey(privateModulus, privateExponent), jsdata);
		System.out.println("================>>> "  + de_test);
	}*/
	//js 传输过来的密文 09eda854e45f3b2298703e75d45bc6b61a9f3008db1e0dd68e730a0c33aa321998a65dcfd03b0e28ad9bdbefcc99f29b99c434ef9a67b2f58a16cfb70b6a8eebe9fc768895a3c093a43aa4629154f99616c87e6ce90a0dd1a408dd3d9e5d46e98eed4f9a14440546f005badd011b7efb5f36536c9d8f1682d8681e993e755b79  

	
	
}
