package common.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����base64�ļ����㷨
 * 
 * @author renjie120 419723443@qq.com
 * 
 */
public class Cdd2 {
	static int w;// 3-����ʱ�������3���µ���

	static char[] source;

	private static class SingletonHolder {
		public final static Cdd2 instance = new Cdd2();
	}

	public static Cdd2 getInstance() {
		return SingletonHolder.instance;
	}

	public static String DJPHttp = "qC8lS#^P2s%QS@v&pM!QRsuKOszJ2s4dRb8dSYz&pN4nUM!l8cdIpizrSsLn9N7z3i3Q3ijs0b4dScdXR#lm4iOm5#BQ4i$t5iOQ3ik=";
	public static String KEY = "B!@#$%DCj0123456789ikLNMOpqRSTUVWXYZ&dcb^*()IJKPQonmlrstuvwxyz+/=";
	public static String KEY2 = "B!@#$%DCj012pqRSTcb^*()IJKtuWlUVL&d678PXYMOsZ9nmQo45Nr3ikvwxyz+/=";

	private static char[] strToChars(String str) {
		try {
			byte[] temp;
			temp = str.getBytes(System.getProperty("file.encoding"));
			int len = temp.length;
			char[] oldStrbyte = new char[len];
			for (int i = 0; i < len; i++) {
				char hh = (char) temp[i];
				if (temp[i] < 0) {
					hh = (char) (temp[i] + 256);
				}
				oldStrbyte[i] = hh;
			}
			return oldStrbyte;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �����ַ�����
	 * 
	 * @param str
	 *            ��Կ��
	 * @param ����
	 * @return
	 */
	public String strToBase64Str(String str, String mima) {
		char[] oldStrbyte = Cdd2.strToChars(str);
		char[] ansChars = toAsBase64String(oldStrbyte, mima);
		return new String(ansChars);
	}

	public static void main(String[] a) {
		Cdd2 c = new Cdd2();
		List list = new ArrayList();
		list.add(1);
		list.add("c");
		list.add((int) 'a');
		String mima = c
				.strToBase64Str(
						"http://app.deppon.com/center/decryptFile?userId=130126&serial=356380051796015",
						"B!@#$%DCj0123456789ikLNMOpqRSTUVWXYZ&dcb^*()IJKPQonmlrstuvwxyz+/=");
		System.out.println(mima);
		try {
			System.out
					.println(c
							.strFromAsBase64Str(mima,
									"B!@#$%DCj0123456789ikLNMOpqRSTUVWXYZ&dcb^*()IJKPQonmlrstuvwxyz+/="));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static char[] toAsBase64String(char[] input, String mima) {
		char[] Base64Code = Cdd2.strToChars(mima);
		source = input;
		int messageLen = input.length;
		int page = messageLen / 3;
		int messageLen2;
		if ((messageLen % 3) > 0) {
			page++;
			w = 3 - messageLen % 3;
		}
		messageLen2 = messageLen + w;
		char[] before = new char[messageLen2];
		for (int x = 0; x < messageLen2; x++) {
			if (x < messageLen) {
				before[x] = source[x];
			} else {// �����ϵ���������Ϊ0
				before[x] = 0;
			}
		}

		char[] instr = new char[3];
		char[] result = new char[page * 4];
		byte[] buffer = new byte[page * 4];
		for (int i = 0; i < page; i++) {
			instr[0] = before[i * 3];
			instr[1] = before[i * 3 + 1];
			instr[2] = before[i * 3 + 2];
			buffer[0 + i * 4] = (byte) (instr[0] >> 2);
			buffer[1 + i * 4] = (byte) (((instr[0] & 0x03) << 4) ^ (instr[1] >> 4));
			if (instr[1] != 0) {
				buffer[2 + i * 4] = (byte) (((instr[1] & 0x0f) << 2) ^ (instr[2] >> 6));
			} else {
				buffer[2 + i * 4] = 64;
			}
			if (instr[2] != 0) {
				buffer[3 + i * 4] = (byte) (instr[2] & 0x3f);
			} else {
				buffer[3 + i * 4] = 64;// ��һ���ز����٣����������Ļ�����Ȼ��Base64��
			}
			// ��64�����õĴ��룬ʵ��Ҫ�õ���65�����������������=�����Ժ�Ҫ����ȥ���ģ���
		}
		for (int x = 0; x < page * 4; x++) {
			result[x] = Base64Code[buffer[x]];
		}
		return result;
	}

	/**
	 * �����ַ�����
	 * 
	 * @param str
	 *            ���ܴ���
	 * @return
	 * @throws Exception
	 */
	public String strFromAsBase64Str(String str, String mima) throws Exception {
		byte[] ansBytes = fromAsBase64String(str, mima);
		String ans = new String(ansBytes, System.getProperty("file.encoding"));
		return ans;
	}

	private static byte[] fromAsBase64String(String Message1, String mima)
			throws Exception {
		int q = 0;// ����ͳ���ж��ٸ�ת���ַ���
		for (int a = 0; a < Message1.length(); a++) {
			byte x = (byte) Message1.charAt(a);
			if (x == 10 || x == 13 || x == 9 || x == 32) {
				q++;
			}
		}
		char[] message = new char[Message1.length() - q];
		int d = 0;
		for (int a = 0; a < Message1.length(); a++) {
			byte x = (byte) Message1.charAt(a);
			if (x == 10 || x == 13 || x == 9 || x == 32) {
				d++;
				continue;
			} else {
				/* AEFGH */
				message[a - d] = Message1.charAt(a);
			}
		}
		String Message = new String(message);// ������ʦ������������������������
		String regEx = "^[BCDI-Zbcdi-z0-9^&*()!@#$%/+=]*$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(Message);
		boolean ans = m.matches();
		int iq = m.end();
		if (!ans) {
			System.out.print("��������ȷ��BASE64���룬���顣");
			throw new Exception("��������ȷ��BASE64���룬���顣");
		}
		if (((Message.length()) % 4) != 0) {
			System.out.print("������ȷ��BASE64���룬����");
			throw new Exception("������ȷ��BASE64���룬���顣");
		}
		String Base64Code = mima;
		int page = Message.length() / 4;
		byte[] buffer = new byte[page * 3];
		int length;
		int temp = 0;// ����������3���ַ����ж��ٸ���=��
		for (int x = 0; x < 2; x++) {
			if (message[Message.length() - x - 1] == '=') {
				temp++;
			}
		}
		length = buffer.length - temp;
		byte[] newStr = new byte[length];
		byte[] outstr = new byte[3 * page];// ������outMessage������ȫ�����Ƶ�����ð�����������ȥ.
		for (int i = 0; i < page; i++) {
			byte[] instr = new byte[4];// �������������base64����Ķ�Ӧ������0��64����һ��
			instr[0] = (byte) Base64Code.indexOf(message[i * 4]);
			instr[1] = (byte) Base64Code.indexOf(message[i * 4 + 1]);
			instr[2] = (byte) Base64Code.indexOf(message[i * 4 + 2]);
			instr[3] = (byte) Base64Code.indexOf(message[i * 4 + 3]);
			outstr[0 + i * 3] = (byte) ((instr[0] << 2) ^ ((instr[1] & 0x30) >> 4));
			if (instr[2] != 64) {
				outstr[1 + i * 3] = (byte) ((instr[1] << 4) ^ ((instr[2] & 0x3c) >> 2));
			} else {
				outstr[2 + i * 3] = 0;
			}
			if (instr[3] != 64) {
				outstr[2 + i * 3] = (byte) ((instr[2] << 6) ^ instr[3]);
			} else {
				outstr[2 + i * 3] = 0;
			}
		}

		for (int o = 0; o < length; o++) {
			newStr[o] = outstr[o];
		}
		return newStr;
	}
}
