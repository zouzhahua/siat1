
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class test6 {
	// 随机串
	static String key = "";

	public static String genRandomNum(int pwd_len) {
		// 35是因为数组是从0开始的，26个字母+10个数字
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		// StringBuffer pwd = new StringBuffer("");
		String pwd = "";
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				// pwd.append(str[i]);
				pwd += str[i];
				count++;
			}
		}
		return pwd;
	}

	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}

	public static String decrypt(byte[] content, String key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			byte[] result = cipher.doFinal(content);
			return new String(result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String MD5(String key) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = key.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	// 服务器
	public static void testServer() {
		// 创建一个服务器
		boolean flag;
		String str3 = "";
		System.out.println("等待客户端连接。。。");
		PrintWriter pwtoclien = null;
		Scanner keybordscanner = null;
		Scanner inScanner = null;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(6666);
			// 创建一个接收连接客户端的对象
			Socket socket = ss.accept();
			System.out.println(socket.getInetAddress() + "已成功连接到此台服务器上。");
			// 字符输出流
			pwtoclien = new PrintWriter(socket.getOutputStream());
			System.out.println("开始认证");
			String str = genRandomNum(10);
			pwtoclien.println(str);
			pwtoclien.flush();
			keybordscanner = new Scanner(System.in);
			inScanner = new Scanner(socket.getInputStream());
			String str1 = inScanner.nextLine();
			System.out.println("客户端的密文为" + str1);
			byte[] buffer = getContent("test.txt");
//			byte[] thekey = new byte[122];
//			System.arraycopy(buffer, 6, thekey, 0, 122);
			byte[] thekey = new byte[10];
			System.arraycopy(buffer, 6, thekey, 0, 10);
			key = new String(thekey);
			String MD5_String;
			MD5_String = MD5(str + " " + key);
			System.out.println("服务端的密文为" + MD5_String);
			// 修改认证是否成功
			if (MD5_String.equals(str1)) {
				System.out.println("认证成功");
				str3 = "认证成功";
				flag = true;
			} else {
				System.out.println("认证失败");
				str3 = "认证失败";
				flag = false;
			}
			pwtoclien.println(str3);
			pwtoclien.flush();
			DataInputStream in;
			in = new DataInputStream(socket.getInputStream());
			while (flag) {
				byte[] indata = new byte[1000];

				int size = in.read(indata);
				if(size<1)
					break;
				byte[] indata1 = new byte[size];
				System.arraycopy(indata, 0, indata1, 0, size);
				
				String decryResult = decrypt(indata1, key);
				if (decryResult.equals("exit")) {
					flag = false;
					System.out.println(decryResult);
				} else if(decryResult.equals("over")){
					flag = false;
					System.out.println("u盾被拔出");
					break;
				}else {
					System.out.println("客戶端加密內容：" + new String(indata1));
			//		System.out.println("key为：" + key);
					
					System.out.println(decryResult);
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			pwtoclien.close();
			keybordscanner.close();
			inScanner.close();
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		testServer();
	}
}