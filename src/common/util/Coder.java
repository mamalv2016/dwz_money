package common.util;

import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * ��������㷨����.
 * 
 * @author renjie120 connect my:(QQ)1246910068
 * 
 */
public abstract class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC�㷨��ѡ���¶����㷨
	 * 
	 * <pre>
	 * HmacMD5  
	 * HmacSHA1  
	 * HmacSHA256  
	 * HmacSHA384  
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		// return (new BASE64Decoder()).decodeBuffer(key);
		return Base64.decodeBase64(key);
	}

	/**
	 * BASE64����
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		// return (new BASE64Encoder()).encodeBuffer(key);
		return Base64.encodeBase64String(key);
	}

	/**
	 * ����.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String toMyCoder(String key)   {
		String ans = Cdd2.getInstance().strToBase64Str(key, Cdd2.KEY2);
//		byte[] inputData = key.getBytes();
//		// �Ƚ���base64����
//		String ans = Coder.encryptBASE64(inputData);
//		// �ڽ��и��Ի��ļ���.
//		ans = PassWord.encode(ans);
		return ans;
	}

	/**
	 * ����.
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String fromMyCoder(String key) {
//		key = PassWord.decode(key);
//		// �ٽ���base64����
//		byte[] output;
//		String outputStr = "";
//		try {
//			output = Coder.decryptBASE64(key);
//			outputStr = new String(output);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String outputStr;
		try {
			outputStr = Cdd2.getInstance().strFromAsBase64Str(key, Cdd2.KEY2);
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
		return outputStr;
	}

	public static void main(String[] args) {
//		System.out.println(fromMyCoder("FsVc`4CL@fUxCm=W").trim());
		try { 
			System.out.println(getMyCoder("hjsdkjshkdj"));
			System.out.println(toMyCoder("1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * MD5����
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * �õ�md5�����ַ���.
	 * 
	 * @Title: encryptMD5Str
	 * @Description: TODO(������һ�仰�����������������)
	 * @param @param data
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @throws
	 */
	public static String encryptMD5Str(String data)  {
//		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
//		md5.update(data.getBytes());
//		byte[] ans = md5.digest();
//		BigInteger inT = new BigInteger(ans);
//		return inT.toString(16);
		return DigestUtils.md5Hex(data);
	}
	
	public static String CODER_SEED="ido!@#";
	/**
	 * �����µ�����,ʹ��MD5�����㷨.
	 * @param data
	 * @return
	 */
	public static String getMyCoder(String data){
		return DigestUtils.md5Hex(CODER_SEED+data).toUpperCase();
	}
	 
	/**
	 * SHA����
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * ��ʼ��HMAC��Կ
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC����
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}
}