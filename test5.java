
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import SuperDog.Dog;
import SuperDog.DogApiVersion;
import SuperDog.DogStatus;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Random;
import java.util.Scanner;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.sql.*;

public class test5 {
	private static String dbUrl1 = "jdbc:mysql://localhost:3306/vemsnew?characterEncoding=utf8&useSSL=false";
	// 用户名
	private static String dbUserName = "ips";
	// 密码
	private static String dbPassword = "Ips@_123";
	// 驱动名称
	private static String jdbcName = "com.mysql.jdbc.Driver";
	static ResultSet rs1 = null;
	// public static final int DEMO_MEMBUFFER_SIZE = 128;
	public static final int DEMO_MEMBUFFER_SIZE = 16;
	static String key = "";
	public static final String vendorCode = new String(
			"AzIceaqfA1hX5wS+M8cGnYh5ceevUnOZIzJBbXFD6dgf3tBkb9cvUF/Tkd/iKu2fsg9wAysYKw7RMA"
					+ "sVvIp4KcXle/v1RaXrLVnNBJ2H2DmrbUMOZbQUFXe698qmJsqNpLXRA367xpZ54i8kC5DTXwDhfxWT"
					+ "OZrBrh5sRKHcoVLumztIQjgWh37AzmSd1bLOfUGI0xjAL9zJWO3fRaeB0NS2KlmoKaVT5Y04zZEc06"
					+ "waU2r6AU2Dc4uipJqJmObqKM+tfNKAS0rZr5IudRiC7pUwnmtaHRe5fgSI8M7yvypvm+13Wm4Gwd4V"
					+ "nYiZvSxf8ImN3ZOG9wEzfyMIlH2+rKPUVHI+igsqla0Wd9m7ZUR9vFotj1uYV0OzG7hX0+huN2E/Id"
					+ "gLDjbiapj1e2fKHrMmGFaIvI6xzzJIQJF9GiRZ7+0jNFLKSyzX/K3JAyFrIPObfwM+y+zAgE1sWcZ1"
					+ "YnuBhICyRHBhaJDKIZL8MywrEfB2yF+R3k9wFG1oN48gSLyfrfEKuB/qgNp+BeTruWUk0AwRE9XVMU"
					+ "uRbjpxa4YA67SKunFEgFGgUfHBeHJTivvUl0u4Dki1UKAT973P+nXy2O0u239If/kRpNUVhMg8kpk7"
					+ "s8i6Arp7l/705/bLCx4kN5hHHSXIqkiG9tHdeNV8VYo5+72hgaCx3/uVoVLmtvxbOIvo120uTJbuLV"
					+ "TvT8KtsOlb3DxwUrwLzaEMoAQAFk6Q9bNipHxfkRQER4kR7IYTMzSoW5mxh3H9O8Ge5BqVeYMEW36q"
					+ "9wnOYfxOLNw6yQMf8f9sJN4KhZty02xm707S7VEfJJ1KNq7b5pP/3RjE0IKtB2gE6vAPRvRLzEohu0"
					+ "m7q1aUp8wAvSiqjZy7FLaTtLEApXYvLvz6PEJdj4TegCZugj7c8bIOEqLXmloZ6EgVnjQ7/ttys7VF"
					+ "ITB3mazzFiyQuKf4J6+b/a/Y");

	public static final byte[] data = { 0x74, 0x65, 0x73, 0x74, 0x20, 0x73, 0x74, 0x72, 0x69, 0x6e, 0x67, 0x20, 0x31,
			0x32, 0x33, 0x34 };

	/************************************************************************
	 *
	 * helper function: dumps a given block of data, in hex and ascii
	 */

	/*
	 * Converts a byte to hex digit and writes to the supplied buffer
	 */
	private static void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	/*
	 * Converts a byte array to hex string
	 */
	private static String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();

		int len = block.length;

		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}

	public static void dump(byte[] data, String margin) {
		int i, j;
		byte b;
		byte[] s = new byte[16];
		byte hex[] = { 0 };
		String shex;
		String PrtString;

		if (data.length == 0)
			return;

		s[0] = 0;
		j = 0;
		for (i = 0; i < data.length; i++) {
			if (j == 0)
				System.out.print(margin);
			b = data[i];
			if ((b < 32) || (b > 127))
				s[j] = '.';
			else
				s[j] = b;
			if (j < 15)
				s[j + 1] = 0;
			hex[0] = b;
			shex = toHexString(hex);
			System.out.print(shex + " ");
			j++;
			if (((j & 3) == 0) && (j < 15))
				System.out.print("| ");
			PrtString = new String(s);
			if (j > 15) {
				System.out.println("[" + PrtString + "]");
				j = 0;
				s[0] = 0;
			}
		}
		if (j != 0) {
			while (j < 16) {
				System.out.print("   ");
				j++;
				if (((j & 3) == 0) && (j < 15))
					System.out.print("| ");
			}
			PrtString = new String(s);
			System.out.println(" [" + PrtString + "]");
		}
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

	public static byte[] encrypt(String content, String key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			byte[] result = cipher.doFinal(content.getBytes());
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getthekey() {
		String key = "";
		int status;
		String str3 = "";// 用户名
		int i;
		int fsize;
		int input = 0;
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);
		String str2 = "";// 密钥
		Statement sm = null;
		ResultSet rs = null;
		Connection con = null;

		Dog curDog = new Dog(Dog.DOG_DEFAULT_FID);
		// System.out.println("\nThis is a simple demo program for SuperDog licensing
		// API\n");
		// System.out.println("Copyright (C) SafeNet, Inc. All rights reserved.\n\n");

		DogApiVersion version = curDog.getVersion(vendorCode);
		status = version.getLastError();

		switch (status) {
		case DogStatus.DOG_STATUS_OK:
			break;
		default:
			System.out.println("unexpected error");
		}

		// System.out.println(
		// "API Version: " + version.majorVersion() + "." + version.minorVersion() + "."
		// + version.buildNumber());

		/**********************************************************************
		 * dog_login establish a context for SuperDog
		 */

		System.out.print("login to default feature         : ");

		/* login feature 0 */
		/* this default feature is available in any SuperDog */
		boolean flag1 = true;
		while (flag1) {
			curDog.login(vendorCode);
			status = curDog.getLastError();
			switch (status) {
			case DogStatus.DOG_STATUS_OK:
				System.out.println("ukey已经插入");
				flag1 = false;
				break;

			default:
				System.out.println("ukey未插入");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/********************************************************************
		 * dog_get_size retrieve the data file size of SuperDog
		 */

		// System.out.println("\nretrieving the data file size : ");

		fsize = curDog.getSize(Dog.DOG_FILEID_RW);
		status = curDog.getLastError();

		switch (status) {
		case DogStatus.DOG_STATUS_OK:
			// System.out.println("file size is " + fsize + " bytes");
			break;
		case DogStatus.DOG_INV_HND:
			System.out.println("handle not active");
			break;
		case DogStatus.DOG_INV_FILEID:
			System.out.println("invalid file id");
			break;
		case DogStatus.DOG_NOT_FOUND:
			System.out.println("SuperDog not found");
			break;
		case DogStatus.DOG_LOCAL_COMM_ERR:
			System.out.println("communication error between API and local SuperDog License Manager");
			break;
		default:
			System.out.println("could not retrieve data file size");
		}
		if (fsize != 0) {
			if (fsize > DEMO_MEMBUFFER_SIZE)
				fsize = DEMO_MEMBUFFER_SIZE;

			System.out.println("\nreading " + fsize + " bytes from data file   : ");

			byte[] membuffer = new byte[DEMO_MEMBUFFER_SIZE];

			curDog.read(Dog.DOG_FILEID_RW, 0, membuffer);
			status = curDog.getLastError();

			switch (status) {
			case DogStatus.DOG_STATUS_OK:
				System.out.println("OK");
				dump(membuffer, "    ");
				break;

			default:
				System.out.println("read data file failed");
			}
			// byte[] thekey = new byte[122];
			// System.arraycopy(membuffer, 6, thekey, 0, 122);
			byte[] thekey = new byte[10];
			System.arraycopy(membuffer, 6, thekey, 0, 10);
			str2 = new String(thekey);
			byte[] thekey1 = new byte[6];
			System.arraycopy(membuffer, 0, thekey1, 0, 6);
			str3 = new String(thekey1);
		}
		flag1 = false;
		try {
			// 获取数据库连接
			con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
			sm = con.createStatement();
			rs = sm.executeQuery("select * from vemsnew.admin limit 0,30");

			while (rs.next()) {
				System.out.println(rs.getString("name") + "  " + rs.getString("password"));
				// System.out.println("name:" + rs.getString("enter_car_license_number"));
				if (str3.equals(rs.getString("name")) && rs.getString("password").equals(str2)) {
					System.out.println("用户 " + str3 + ": 登录成功");
					flag1 = true;
					break;
				}

			}
			// rs1=rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取数据库连接失败！");
		}
		if (!flag1) {
			System.out.println(str3 + "无此用户");
			str2 = "";
		}
		curDog.logout();
		// System.out.println(str2);
		return str2;
	}

	public static void testClient() {

	}

	public static void main(String[] args) {
		int status;
		String info;
		boolean flag = true;
		// testClient();
		// System.out.print(key);
		System.out.println("正在向服务器请求连接。。。");
		Socket socket = null;
		Scanner keybordscanner = null;
		Scanner inScanner = null;
		PrintWriter pwtoserver = null;
		try {
			Class.forName(jdbcName);

			// System.out.println("加载驱动成功！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加载驱动失败！");
		}
		try {
			socket = new Socket("127.0.0.1", 6666);
			inScanner = new Scanner(socket.getInputStream());

			pwtoserver = new PrintWriter(socket.getOutputStream());
			System.out.print("我(客户端)：");
			String str = inScanner.nextLine();// 获得随机串
			System.out.println(str);
			key = getthekey();
			if (key.equals("")) {
				flag = false;
			} else {
				System.out.println(key);
				String MD5_String;
				MD5_String = MD5(str + " " + key);
				pwtoserver.println(MD5_String);
				pwtoserver.flush();
				System.out.println("md5加密后" + MD5_String);
				String str2 = inScanner.nextLine();
				if ("认证成功".equals(str2)) {
					System.out.println("认证成功");

					flag = true;
				} else {
					System.out.println("认证失败");

					flag = false;
				}
			}

			// 先读取键盘录入方可向服务端发送消息
			Dog curDog = new Dog(Dog.DOG_DEFAULT_FID);
			// curDog.login(vendorCode);
			keybordscanner = new Scanner(System.in);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			Statement sm = null;
			ResultSet rs = null;
			Connection con = null;
			try {
				// 获取数据库连接
				con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
				sm = con.createStatement();
				rs = sm.executeQuery("select * from vemsnew.tb_realtime_inout");
				// rs = sm.executeQuery(
				// "select * from vemsnew.tb_record_car left join vemsnew.tb_parking_lot on
				// vemsnew.tb_record_car.parking_lot_seq=vemsnew.tb_parking_lot.parking_lot_seq
				// limit 0,1000");
				// System.out.println("获取数据库连接成功！");
				// System.out.println("进行数据库操作！");
				// while (rs.next()) {
				// System.out.println("name:" + rs.getString("enter_car_license_number"));
				// }
				rs1 = rs;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("获取数据库连接失败！");
			}

			try {
				while (flag && rs1.next()) {

					// System.out.println("输入要加密内容");
					// String keyborddata = keybordscanner.nextLine();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String keyborddata = "";
					// String parking_lot = rs1.getString("parking_lot_name");
					// String keyborddata = rs1.getString("car_license_number");
					// String etime = rs1.getString("enter_time");
					// String ltime = rs1.getString("leave_time");
					String time = rs1.getString("time");
					String parking_lot_name = rs1.getString("parking_lot_name");
					;
					String car_license_number = rs1.getString("car_license_number");
					String action = rs1.getString("action");
					// String action="";
					// if(action1.equals("enter")) {
					// action = "进入";
					// }else {
					// action = "离开";
					// }
					System.out.println("时间:" + time + "\n停车场：" + parking_lot_name + "\n车辆：" + car_license_number
							+ "\n动作：" + action);
					// System.out.println("车辆："+car_license_number+action+"停车场"+parking_lot_name+"时间"+time);
					curDog.login(vendorCode);
					status = curDog.getLastError();
					if (status == DogStatus.DOG_STATUS_OK) {

						String keyborddata1 = keyborddata;

						// byte[] result = encrypt(parking_lot+" "+keyborddata+" "+etime+" "+ltime,
						// key);
						// byte[] result = encrypt(
						// "停车场:" + parking_lot + " 车牌号：" + keyborddata + " 进入时间：" + etime + " 出去时间：" +
						// ltime, key);
						byte[] result = encrypt("时间:" + time + "\n停车场：" + parking_lot_name + "\n车辆："
								+ car_license_number + "\n动作：" + action, key);
						// 展示到己方的控制台
						if (!keyborddata.equals("exit")) {
							System.out.println("加密密文" + new String(result));
						} // 写到服务端的的控制台

						out.write(result, 0, result.length);

						if (keyborddata1.equals("exit")) {
							flag = false;
						}
					} else {

						System.out.println("u盾被拔出，进程结束");
						String keyborddata1 = "over";
						byte[] result = encrypt(keyborddata1, key);
						out.write(result, 0, result.length);
						flag = false;
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// keybordscanner.close();
			pwtoserver.close();
			inScanner.close();
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}