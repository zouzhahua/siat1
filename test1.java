import java.io.*;

import SuperDog.Dog;
import SuperDog.DogApiVersion;
import SuperDog.DogStatus;
import java.sql.*;

import java.security.NoSuchAlgorithmException;

import java.util.Random;
import java.util.Scanner;

public class test1 {
	public static final int DEMO_MEMBUFFER_SIZE = 128;
	private static String dbUrl1 = "jdbc:mysql://localhost:3306/vemsnew?characterEncoding=utf8&useSSL=false";
	// 用户名
	private static String dbUserName = "ips";
	// 密码
	private static String dbPassword = "Ips@_123";
	// 驱动名称
	private static String jdbcName = "com.mysql.jdbc.Driver";
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

	public static void main(String argv[]) throws java.io.IOException, NoSuchAlgorithmException {
		int status;
		String info;
		int i;
		int fsize;
		int input = 0;
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(reader);

		Dog curDog = new Dog(Dog.DOG_DEFAULT_FID);
		System.out.println("\nThis is a simple demo program for SuperDog licensing API\n");
		System.out.println("Copyright (C) SafeNet, Inc. All rights reserved.\n\n");

		DogApiVersion version = curDog.getVersion(vendorCode);
		status = version.getLastError();

		switch (status) {
		case DogStatus.DOG_STATUS_OK:
			break;
		case DogStatus.DOG_NO_API_DYLIB:
			System.out.println("SuperDog API dynamic library not found");
			return;
		case DogStatus.DOG_INV_API_DYLIB:
			System.out.println("SuperDog API dynamic library is corrupt");
			return;
		default:
			System.out.println("unexpected error");
		}

		System.out.println("API Version: " + version.majorVersion() + "." + version.minorVersion() + "."
				+ version.buildNumber() );

		/**********************************************************************
		 * dog_login establish a context for SuperDog
		 */

		System.out.print("login to default feature         : ");

		/* login feature 0 */
		/* this default feature is available in any SuperDog */
		curDog.login(vendorCode);
		status = curDog.getLastError();

		switch (status) {
		case DogStatus.DOG_STATUS_OK:
			System.out.println("OK");
			break;
		case DogStatus.DOG_FEATURE_NOT_FOUND:
			System.out.println("no SuperDog DEMOMA key found");
			break;
		case DogStatus.DOG_NOT_FOUND:
			System.out.println("SuperDog not found");
			break;
		case DogStatus.DOG_INV_VCODE:
			System.out.println("invalid vendor code");
			break;
		case DogStatus.DOG_LOCAL_COMM_ERR:
			System.out.println("communication error between API and local SuperDog License Manager");
			break;
		default:
			System.out.println("login to default feature failed");
		}

		/********************************************************************
		 * dog_get_sessioninfo retrieve SuperDog attributes
		 */

//		System.out.print("\nget session info                 : ");
//
//		info = curDog.getSessionInfo(Dog.DOG_KEYINFO);
//		status = curDog.getLastError();
//
//		switch (status) {
//		case DogStatus.DOG_STATUS_OK:
//			System.out.print("OK, SuperDog attributes retrieved\n\n" + "SuperDog info:\n===============\n" + info
//					+ "\n===============\n");
//			break;
//		case DogStatus.DOG_INV_HND:
//			System.out.println("handle not active");
//			break;
//		case DogStatus.DOG_INV_FORMAT:
//			System.out.println("unrecognized format");
//			break;
//		case DogStatus.DOG_NOT_FOUND:
//			System.out.println("SuperDog not found");
//			break;
//		case DogStatus.DOG_LOCAL_COMM_ERR:
//			System.out.println("communication error between API and local SuperDog License Manager");
//			break;
//		default:
//			System.out.println("dog_get_sessioninfo failed");
//		}

		/*******************************************************************/

//		System.out.println("\npress ENTER to continue");
//		input = in.read();

		/********************************************************************
		 * dog_get_size retrieve the data file size of SuperDog
		 */

		System.out.println("\nretrieving the data file size : ");

		fsize = curDog.getSize(Dog.DOG_FILEID_RW);
		status = curDog.getLastError();

		switch (status) {
		case DogStatus.DOG_STATUS_OK:
			System.out.println("file size is " + fsize + " bytes");
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
			/* skip data file access if no data file available */

			/******************************************************************
			 * dog_read read data file
			 */

			/* limit file size to be used in this demo program */

			if (fsize > DEMO_MEMBUFFER_SIZE)
				fsize = DEMO_MEMBUFFER_SIZE;

			System.out.println("\nreading " + fsize + " bytes from data file   : ");

			//byte[] membuffer = new byte[DEMO_MEMBUFFER_SIZE];
			byte[] membuffer = new byte[16];

			curDog.read(Dog.DOG_FILEID_RW, 0, membuffer);
			status = curDog.getLastError();

			switch (status) {
			case DogStatus.DOG_STATUS_OK:
				System.out.println("OK");
				dump(membuffer, "    ");
				break;
			case DogStatus.DOG_INV_HND:
				System.out.println("handle not active");
				break;
			case DogStatus.DOG_INV_FILEID:
				System.out.println("invalid file id");
				break;
			case DogStatus.DOG_MEM_RANGE:
				System.out.println("beyond memory range of attached SuperDog");
				break;
			case DogStatus.DOG_NOT_FOUND:
				System.out.println("SuperDog not found");
				break;
			case DogStatus.DOG_LOCAL_COMM_ERR:
				System.out.println("communication error between API and local SuperDog License Manager");
				break;
			default:
				System.out.println("read data file failed");
			}

			/******************************************************************
			 * dog_write write to data file
			 */
			try {
				Class.forName(jdbcName);

		//		System.out.println("加载驱动成功！");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("加载驱动失败！");
			}
			ResultSet rs = null;
			Connection con = null;
			PreparedStatement stmt;
			String sql = "INSERT INTO admin(name, password) " + " VALUES( ?, ?)";
			
			System.out.println("\nincrementing every byte in data file buffer");

			Scanner scan = new Scanner(System.in);
			System.out.println("请输入6位用户名");
			
			String str1 = scan.nextLine();

			byte[] buffer1 = str1.getBytes();
			scan.close();
		//	String str2 = genRandomNum(122);
			String str2 = genRandomNum(10);
			try {
				// 获取数据库连接
				con = DriverManager.getConnection(dbUrl1, dbUserName, dbPassword);
				
				stmt = (PreparedStatement) con.prepareStatement(sql);
//				System.out.println("获取数据库连接成功！");
//				System.out.println("进行数据库操作！");
				stmt.setString(1, str1);
				stmt.setString(2, str2);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("获取数据库连接失败！");
			}
			byte[] buffer2 = str2.getBytes();
//			for (i = 0; i < 128; i++) {
//				if (i < 6) {
//					membuffer[i] = buffer1[i];
//				} else {
//					membuffer[i] = buffer2[i - 6];
//				}
//			}
			for(i=0;i<16;i++) {
				if(i<6) {
					membuffer[i] = buffer1[i];
				}else {
					membuffer[i] = buffer2[i - 6];
				}
			}
			System.out.println("\nwriting " + fsize + " bytes to data file     : ");

			curDog.write(Dog.DOG_FILEID_RW, 0, membuffer);
			status = curDog.getLastError();
			
//			try {
//				OutputStream os = new FileOutputStream("test.txt");
//				for(i=0;i<128;i++) {
//					os.write(membuffer[i]);
//				}
//				os.close();
//				InputStream is = new FileInputStream("test.txt");
//				for(i=0;i<128;i++) {
//					System.out.print((char)is.read());
//				}
//				is.close();
//				System.out.println();
//			}
			try {
				OutputStream os = new FileOutputStream("test.txt");
				for(i=0;i<16;i++) {
					os.write(membuffer[i]);
				}
				os.close();
				InputStream is = new FileInputStream("test.txt");
				for(i=0;i<16;i++) {
					System.out.print((char)is.read());
				}
				is.close();
				System.out.println();
			}
			catch(IOException e) {
				System.out.print("Exception");
			}
			switch (status) {
			case DogStatus.DOG_STATUS_OK:
				System.out.println("OK");
				break;
			case DogStatus.DOG_INV_HND:
				System.out.println("handle not active");
				break;
			case DogStatus.DOG_INV_FILEID:
				System.out.println("invalid file id");
				break;
			case DogStatus.DOG_MEM_RANGE:
				System.out.println("beyond memory range of attached SuperDog");
				break;
			case DogStatus.DOG_NOT_FOUND:
				System.out.println("SuperDog not found");
				break;
			case DogStatus.DOG_LOCAL_COMM_ERR:
				System.out.println("communication error between API and local SuperDog License Manager");
				break;
			default:
				System.out.println("write data file failed");
			}

			/******************************************************************
			 * dog_read read data file
			 */

			System.out.println("\nreading " + fsize + " bytes from data file   : ");

			curDog.read(Dog.DOG_FILEID_RW, 0, membuffer);

			switch (status) {
			case DogStatus.DOG_STATUS_OK:
				System.out.println("OK");
				dump(membuffer, "    ");
				break;
			case DogStatus.DOG_INV_HND:
				System.out.println("handle not active\n");
				break;
			case DogStatus.DOG_INV_FILEID:
				System.out.println("invalid file id");
				break;
			case DogStatus.DOG_MEM_RANGE:
				System.out.println("beyond memory range of attached SuperDog");
				break;
			case DogStatus.DOG_NOT_FOUND:
				System.out.println("SuperDog not found");
				break;
			case DogStatus.DOG_LOCAL_COMM_ERR:
				System.out.println("communication error between API and local SuperDog License Manager");
				break;
			default:
				System.out.println("read data file failed");
			}

		}
	}

}
